package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao{

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select ID, NAME from Genres", new GenreMapper());
    }

    @Override
    public long count() {
        Long cnt = jdbc.queryForObject("select count(*) from Genres", Map.of(), Long.class);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(jdbc.queryForObject("select ID, NAME from Genres where id = :id", Map.of("id", id), new GenreMapper()));
    }

    @Override
    public void save(Genre genre) {
        if (genre.getId() == null) {
            jdbc.update("insert into Genres(NAME) values(:name)",
                    mapGenreParameters(genre));
        } else {
            jdbc.update("update Genres set NAME = :name where ID = :id",
                    mapGenreParameters(genre));
        }
    }

    private MapSqlParameterSource mapGenreParameters(Genre genre) {
        return new MapSqlParameterSource(
                Map.of("id", genre.getId() == null ? 0 : genre.getId(),
                        "name", genre.getName()));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getLong("ID"));
            genre.setName(rs.getString("NAME"));
            return genre;
        }
    }
}
