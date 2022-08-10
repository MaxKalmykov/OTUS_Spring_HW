package ru.otus.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
