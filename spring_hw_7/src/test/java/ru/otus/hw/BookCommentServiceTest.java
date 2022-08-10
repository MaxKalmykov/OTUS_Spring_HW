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
import ru.otus.hw.repository.BookCommentRepository;
import ru.otus.hw.service.BookCommentService;
import ru.otus.hw.service.BookService;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({BookCommentService.class, BookService.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса BookCommentService")
class BookCommentServiceTest {

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
    private BookCommentService service;

    @Autowired
    private BookCommentRepository repository;

    @Autowired
    private BookService bookService;

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

        assertThat(service.findAll())
                .usingRecursiveComparison()
                .ignoringFields("book")
                .isEqualTo(expectedBookComments);

    }

    @Test
    @DisplayName("Количество возвращаемых строк")
    void shouldReturnBookCommentsCount() {
        assertEquals(1L, service.count());
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

        assertThat(comment).isEqualTo(service.findById(DEFAULT_BOOK_COMMENT_ID));
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
        service.save(comment);
        assertEquals(2L, service.count());
        assertThat(expectedComment).isEqualTo(service.findById(SECOND_BOOK_COMMENT_ID));

    }

    @Test
    @DisplayName("Удаление записи по ID")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldDeleteById() {
        long count = service.count();
        service.removeById(DEFAULT_BOOK_COMMENT_ID);
        assertThat(count - 1).isEqualTo(service.count());
    }

    public BookComment getDefaultComment(){
        return new BookComment()
                .setId(DEFAULT_BOOK_COMMENT_ID)
                .setText(DEFAULT_BOOK_COMMENT_TEXT)
                .setBook(bookService.findById(DEFAULT_BOOK_ID));
    }
}