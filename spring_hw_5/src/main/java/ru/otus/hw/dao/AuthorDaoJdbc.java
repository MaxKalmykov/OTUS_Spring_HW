package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao{

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> findAll() {
        return jdbc.query("select ID, NAME from Authors", new AuthorMapper());
    }

    @Override
    public long count() {
        Long cnt = jdbc.queryForObject("select count(*) from Authors", Map.of(), Long.class);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(jdbc.queryForObject("select ID, NAME from Authors where id = :id", Map.of("id", id), new AuthorMapper()));
    }

    @Override
    public void save(Author author) {
        if (author.getId() == null) {
            jdbc.update("insert into Authors(NAME) values(:name)",
                    mapAuthorParameters(author));
        } else {
            jdbc.update("update Authors set NAME = :name where ID = :id",
                    mapAuthorParameters(author));
        }
    }

    private MapSqlParameterSource mapAuthorParameters(Author author) {
        return new MapSqlParameterSource(
                Map.of("id", author.getId() == null ? 0 : author.getId(),
                        "name", author.getName()));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("ID"));
            author.setName(rs.getString("NAME"));
            return author;
        }
    }
}
