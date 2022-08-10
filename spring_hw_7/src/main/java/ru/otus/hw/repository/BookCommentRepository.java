package ru.otus.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    void deleteAllByBook(Book book);
}
