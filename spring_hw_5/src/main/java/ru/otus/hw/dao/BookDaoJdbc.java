package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao{

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            jdbc.update("insert into Books(AuthorId, GenreId, NAME) values(:authorId, :genreId, :name)",
                    mapBookParameters(book));
        } else {
            jdbc.update("update Books set AuthorId = :authorId, GenreId = :genreId, NAME = :name where ID = :id",
                    mapBookParameters(book));
        }
    }

    private MapSqlParameterSource mapBookParameters(Book book) {
        return new MapSqlParameterSource(
                Map.of("id", book.getId() == null ? 0 : book.getId(),
                        "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId(),
                        "name", book.getName()));
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        var books= jdbc.query("select" +
                "                    b.ID as BookId" +
                "                   ,b.NAME as BookName" +
                "                   ,b.AuthorId" +
                "                   ,b.GenreId" +
                "                   ,a.NAME as AuthorName" +
                "                   ,g.NAME as GenreName" +
                "               from" +
                "                   Books as b" +
                "               left join Authors as a" +
                "                  on b.AuthorId = a.ID" +
                "               left join Genres as g" +
                "                  on b.GenreId = g.ID" +
                "               where" +
                "                  b.ID = :id",
                Map.of("id", id),
                new BookMapper());

        return books.stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select" +
                        "                    b.ID as BookId" +
                        "                   ,b.NAME as BookName" +
                        "                   ,b.AuthorId" +
                        "                   ,b.GenreId" +
                        "                   ,a.NAME as AuthorName" +
                        "                   ,g.NAME as GenreName" +
                        "               from" +
                        "                   Books as b" +
                        "               left join Authors as a" +
                        "                  on b.AuthorId = a.ID" +
                        "               left join Genres as g" +
                        "                  on b.GenreId = g.ID",
                new BookMapper());
    }

    @Override
    public long count() {
        Long cnt = jdbc.queryForObject("select count(*) from Books",
                Map.of(),
                Long.class);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public void removeById(Long id) {
        jdbc.update("delete from Books where ID = :id",
                Map.of("id", id));
    }

    @SuppressWarnings("SqlWithoutWhere")
    @Override
    public void removeAll() {
        jdbc.update("delete from Books", Map.of());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

            Genre genre = new Genre();
            genre.setId(rs.getLong("GenreId"));
            genre.setName(rs.getString("GenreName"));

            Author author = new Author();
            author.setId(rs.getLong("AuthorId"));
            author.setName(rs.getString("AuthorName"));

            Book book = new Book();
            book.setId(rs.getLong("BookId"));
            book.setName(rs.getString("BookName"));
            book.setGenre(genre);
            book.setAuthor(author);

            return book;
        }
    }
}
