package ru.otus.hw.repository;

import ru.otus.hw.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    long count();
    Optional<Genre> findById(Long id);
    void save(Genre genre);
}
