package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;
import ru.otus.hw.entity.Genre;
import ru.otus.hw.repository.BookRepository;
import ru.otus.hw.repository.BookRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(BookRepositoryImpl.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookRepositoryImpl")
class BookRepositoryImplTest {

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final String DEFAULT_BOOK_NAME = "DefaultBook";
    private static final Long SECOND_BOOK_ID = 2L;
    private static final String SECOND_BOOK_NAME = "DefaultBook2";

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME = "DefaultAuthor";
    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "DefaultGenre";
    private static final Long DEFAULT_COMMENT_ID = 1L;
    private static final String DEFAULT_COMMENT_TEXT = "DefaultComment";


    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("Получение всех записей в таблице")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAllBooks() {

        Book book1 = new Book()
                .setId(DEFAULT_BOOK_ID)
                .setName(DEFAULT_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                .setComments(List.of(new BookComment().setId(DEFAULT_COMMENT_ID).setText(DEFAULT_COMMENT_TEXT)));
        Book book2 = new Book()
                .setId(SECOND_BOOK_ID)
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME));

        List<Book> expectedBooks = List.of(
                book1, book2
        );

        repository.save(new Book()
                    .setName(SECOND_BOOK_NAME)
                    .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                    .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                );

        assertThat(repository.findAll())
                .usingRecursiveComparison()
                .ignoringFields("comments")
                .isEqualTo(expectedBooks);

    }

    @Test
    @DisplayName("Количество возвращаемых строк")
    void shouldReturnBooksCount() {
        assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Поиск записи по ID")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindBookById() {
        var book = new Book()
                .setId(DEFAULT_BOOK_ID)
                .setName(DEFAULT_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                .setComments(List.of(getDefaultComment()));

        assertThat(Optional.of(book)).isEqualTo(repository.findById(DEFAULT_BOOK_ID));
    }

    @Test
    @DisplayName("Сохранение новой записи")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldSave() {
        var book = new Book()
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                .setComments(List.of());
        var expectedBook = new Book()
                .setId(SECOND_BOOK_ID)
                .setName(SECOND_BOOK_NAME)
                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                .setComments(List.of());
        repository.save(book);
        System.out.println(repository.findAll());
        System.out.println(repository.count());
        System.out.println(repository.findById(SECOND_BOOK_ID));
        assertEquals(2L, repository.count());
        assertThat(Optional.of(expectedBook)).isEqualTo(repository.findById(SECOND_BOOK_ID));

    }

    @Test
    @DisplayName("Удаление записи по ID")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldDeleteById() {
        long count = repository.count();
        repository.removeById(DEFAULT_BOOK_ID);
        assertThat(count - 1).isEqualTo(repository.count());
    }

    public BookComment getDefaultComment(){
        return new BookComment()
                .setId(DEFAULT_COMMENT_ID)
                .setText(DEFAULT_COMMENT_TEXT)
                .setBook(repository.findById(DEFAULT_BOOK_ID).orElseThrow());
    }
}