package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.entity.Genre;
import ru.otus.hw.repository.GenreRepository;
import ru.otus.hw.service.GenreService;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(GenreService.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование класса GenreService")
class GenreServiceTest {

    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "DefaultGenre";
    private static final Long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_NAME = "DefaultGenre2";


    @Autowired
    private GenreService service;

    @Autowired
    private GenreRepository repository;

    @Test
    @DisplayName("Получение всех записей в таблице")
    void shouldFindAllGenres() {
        Genre genre1 = new Genre()
        .setId(DEFAULT_GENRE_ID)
        .setName(DEFAULT_GENRE_NAME);
        Genre genre2 = new Genre()
        .setId(SECOND_GENRE_ID)
        .setName(SECOND_GENRE_NAME);

        List<Genre> expectedGenres = List.of(
                genre1, genre2
        );

        repository.save(new Genre().setName(SECOND_GENRE_NAME));

        assertThat(service.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expectedGenres);

    }

    @Test
    @DisplayName("Количество возвращаемых строк")
    void shouldReturnGenresCount() {
        assertEquals(1L, service.count());
    }

    @Test
    @DisplayName("Поиск записи по ID")
    void shouldFindGenreById() {
        var genre = new Genre()
        .setId(DEFAULT_GENRE_ID)
        .setName(DEFAULT_GENRE_NAME);

        assertThat(genre).isEqualTo(service.findById(DEFAULT_GENRE_ID));
    }
}