package ru.otus.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
