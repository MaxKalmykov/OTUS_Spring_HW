package ru.otus.hw;

import org.assertj.core.api.AssertionsForClassTypes;
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
import ru.otus.hw.repository.BookCommentRepository;
import ru.otus.hw.repository.BookCommentRepositoryImpl;
import ru.otus.hw.repository.BookRepository;
import ru.otus.hw.repository.BookRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({BookCommentRepositoryImpl.class, BookRepositoryImpl.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookCommentRepositoryImpl")
class BookCommentRepositoryImplTest {

    private static final Long DEFAULT_BOOK_COMMENT_ID = 1L;
    private static final String DEFAULT_BOOK_COMMENT_TEXT = "DefaultComment";
    private static final Long SECOND_BOOK_COMMENT_ID = 2L;
    private static final String SECOND_BOOK_COMMENT_TEXT = "DefaultComment2";

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final String DEFAULT_BOOK_NAME = "DefaultBook";
    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME = "DefaultAuthor";
    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "DefaultGenre";


    @Autowired
    private BookCommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Получение всех записей в таблице")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindAllBookComments() {

        BookComment comment1 = new BookComment()
                .setId(DEFAULT_BOOK_COMMENT_ID)
                .setText(DEFAULT_BOOK_COMMENT_TEXT)
                .setBook(new Book()
                        .setId(DEFAULT_BOOK_ID)
                        .setName(DEFAULT_BOOK_NAME)
                        .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                        .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME)));
        BookComment comment2 = new BookComment()
                .setId(SECOND_BOOK_COMMENT_ID)
                .setText(SECOND_BOOK_COMMENT_TEXT)
                .setBook(new Book()
                        .setId(DEFAULT_BOOK_ID)
                        .setName(DEFAULT_BOOK_NAME)
                        .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                        .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME)));

        List<BookComment> expectedBookComments = List.of(
                comment1, comment2
        );

        repository.save(new BookComment()
                        .setText(SECOND_BOOK_COMMENT_TEXT)
                        .setBook(new Book()
                                .setId(DEFAULT_BOOK_ID)
                                .setName(DEFAULT_BOOK_NAME)
                                .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                                .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME)))
                );

        assertThat(repository.findAll())
                .usingRecursiveComparison()
                .ignoringFields("book")
                .isEqualTo(expectedBookComments);

    }

    @Test
    @DisplayName("Количество возвращаемых строк")
    void shouldReturnBookCommentsCount() {
        assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Поиск записи по ID")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldFindBookCommentById() {
        var comment = new BookComment()
                .setId(DEFAULT_BOOK_COMMENT_ID)
                .setText(DEFAULT_BOOK_COMMENT_TEXT)
                .setBook(new Book()
                        .setId(DEFAULT_BOOK_ID)
                        .setName(DEFAULT_BOOK_NAME)
                        .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                        .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                        .setComments(List.of(getDefaultComment())));

        assertThat(Optional.of(comment)).isEqualTo(repository.findById(DEFAULT_BOOK_COMMENT_ID));
    }

    @Test
    @DisplayName("Сохранение новой записи")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldSave() {
        var comment = new BookComment()
                .setText(SECOND_BOOK_COMMENT_TEXT)
                .setBook(new Book()
                        .setId(DEFAULT_BOOK_ID)
                        .setName(DEFAULT_BOOK_NAME)
                        .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                        .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME)));
        var expectedComment = new BookComment()
                .setId(SECOND_BOOK_COMMENT_ID)
                .setText(SECOND_BOOK_COMMENT_TEXT)
                .setBook(new Book()
                        .setId(DEFAULT_BOOK_ID)
                        .setName(DEFAULT_BOOK_NAME)
                        .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                        .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME)));
        repository.save(comment);
        assertEquals(2L, repository.count());
        assertThat(Optional.of(expectedComment)).isEqualTo(repository.findById(SECOND_BOOK_COMMENT_ID));

    }

    @Test
    @DisplayName("Удаление записи по ID")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldDeleteById() {
        long count = repository.count();
        repository.removeById(DEFAULT_BOOK_COMMENT_ID);
        assertThat(count - 1).isEqualTo(repository.count());
    }

    @Test
    @DisplayName("Удаление всех записей")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldDeleteAll() {
        var comment = new BookComment()
                .setText(DEFAULT_BOOK_COMMENT_TEXT)
                .setBook(new Book()
                        .setId(DEFAULT_BOOK_ID)
                        .setName(DEFAULT_BOOK_NAME)
                        .setAuthor(new Author().setId(DEFAULT_AUTHOR_ID).setName(DEFAULT_AUTHOR_NAME))
                        .setGenre(new Genre().setId(DEFAULT_GENRE_ID).setName(DEFAULT_GENRE_NAME))
                );

        repository.save(comment);
        long count = repository.count();
        repository.removeAll();
        assertThat(count).isGreaterThan(0);
        AssertionsForClassTypes.assertThat(0).isEqualTo(repository.count());
    }

    public BookComment getDefaultComment(){
        return new BookComment()
                .setId(DEFAULT_BOOK_COMMENT_ID)
                .setText(DEFAULT_BOOK_COMMENT_TEXT)
                .setBook(bookRepository.findById(DEFAULT_BOOK_ID).orElseThrow());
    }
}