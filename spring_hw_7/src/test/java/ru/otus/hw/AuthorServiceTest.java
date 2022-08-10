package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.entity.Author;
import ru.otus.hw.repository.AuthorRepository;
import ru.otus.hw.service.AuthorService;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(AuthorService.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса AuthorService")
class AuthorServiceTest {

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME = "DefaultAuthor";
    private static final Long SECOND_AUTHOR_ID = 2L;
    private static final String SECOND_AUTHOR_NAME = "DefaultAuthor2";


    @Autowired
    private AuthorService service;

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("Получение всех записей в таблице")
    void shouldFindAllAuthors() {
        Author author1 = new Author()
        .setId(DEFAULT_AUTHOR_ID)
        .setName(DEFAULT_AUTHOR_NAME);
        Author author2 = new Author()
        .setId(SECOND_AUTHOR_ID)
        .setName(SECOND_AUTHOR_NAME);

        List<Author> expectedAuthors = List.of(
                author1, author2
        );

        repository.save(new Author().setName(SECOND_AUTHOR_NAME));

        assertThat(service.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedAuthors);

    }

    @Test
    @DisplayName("Количество возвращаемых строк")
    void shouldReturnAuthorsCount() {
        assertEquals(1L, service.count());
    }

    @Test
    @DisplayName("Поиск записи по ID")
    void shouldFindAuthorById() {
        var author = new Author();
        author.setId(DEFAULT_AUTHOR_ID);
        author.setName(DEFAULT_AUTHOR_NAME);

        assertThat(author).isEqualTo(service.findById(DEFAULT_AUTHOR_ID));
    }
}