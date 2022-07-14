package ru.otus.hw.dao;

import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();
    long count();
    Optional<Genre> findById(Long id);
    void save(Genre genre);
}
