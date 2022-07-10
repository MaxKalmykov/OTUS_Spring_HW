package ru.otus.hw;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.dao.BookDao;
import ru.otus.hw.dao.BookDaoJdbc;
import ru.otus.hw.dao.GenreDao;
import ru.otus.hw.dao.GenreDaoJdbc;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(BookDaoJdbc.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookDaoJdbc")
class BookDaoJdbcTest {

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final String DEFAULT_BOOK_NAME = "DefaultBook";
    private static final Long SECOND_BOOK_ID = 2L;
    private static final String SECOND_BOOK_NAME = "DefaultBook2";

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME = "DefaultAuthor";
    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "DefaultGenre";


    @Autowired
    private BookDao dao;

    @Test
    @DisplayName("Получение всех записей в таблице")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAllBooks() {
        Book book1 = new Book()
                .setId(DEFAULT_BOOK_ID)
                .setName(DEFAULT_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));
        Book book2 = new Book()
                .setId(SECOND_BOOK_ID)
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));

        List<Book> expectedBooks = List.of(
                book1, book2
        );

        dao.save(new Book()
                    .setName(SECOND_BOOK_NAME)
                    .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                    .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                );

        assertThat(dao.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedBooks);

    }

    @Test
    @DisplayName("Количество возвращаемых строк")
    void shouldReturnBooksCount() {
        assertEquals(1L, dao.count());
    }

    @Test
    @DisplayName("Поиск записи по ID")
    void shouldFindBookById() {
        var book = new Book()
                .setId(DEFAULT_BOOK_ID)
                .setName(DEFAULT_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));

        assertThat(Optional.of(book)).isEqualTo(dao.findById(DEFAULT_BOOK_ID));
    }

    @Test
    @DisplayName("Сохранение новой записи")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldSave() {
        var book = new Book()
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));

        var expectedBook = new Book()
                .setId(SECOND_BOOK_ID)
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));

        dao.save(book);
        assertEquals(2L, dao.count());
        assertThat(Optional.of(expectedBook)).isEqualTo(dao.findById(SECOND_BOOK_ID));

    }

    @Test
    @DisplayName("Удаление записи по ID")
    void shouldDeleteById() {
        long count = dao.count();
        dao.removeById(DEFAULT_BOOK_ID);
        assertThat(count - 1).isEqualTo(dao.count());
    }

    @Test
    @DisplayName("Удаление всех записей")
    void shouldDeleteAll() {
        var book = new Book()
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));

        dao.save(book);
        long count = dao.count();
        dao.removeAll();
        assertThat(count).isGreaterThan(0);
        AssertionsForClassTypes.assertThat(0).isEqualTo(dao.count());
    }
}