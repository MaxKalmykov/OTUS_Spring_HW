package ru.otus.hw.repository;

import ru.otus.hw.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    long count();
    Optional<Author> findById(Long id);
    void save(Author author);
}
