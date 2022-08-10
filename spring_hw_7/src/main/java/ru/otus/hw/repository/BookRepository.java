package ru.otus.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
