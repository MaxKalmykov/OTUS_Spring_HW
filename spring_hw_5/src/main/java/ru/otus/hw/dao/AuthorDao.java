package ru.otus.hw.dao;

import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAll();
    long count();
    Optional<Author> findById(Long id);
    void save(Author author);
}
