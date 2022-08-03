package ru.otus.hw.repository;

import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    void save(BookComment comment);
    List<BookComment> findAll();
    Optional<BookComment> findById(Long id);
    //List<BookComment> findAllByBook(Book book);
    long count();
    void removeById(Long id);
    void removeAllByBook(Book book);
}
