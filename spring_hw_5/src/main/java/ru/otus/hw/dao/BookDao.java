package ru.otus.hw.dao;

import ru.otus.hw.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    long count();
    void removeById(Long id);
    void removeAll();
}
