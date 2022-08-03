package ru.otus.hw.repository;

import ru.otus.hw.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    long count();
    void removeById(Long id);
}
