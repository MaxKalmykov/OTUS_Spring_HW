package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.entity.Genre;
import ru.otus.hw.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService implements NoCrudService<Genre> {

    private final GenreRepository repository;

    @Override
    public Genre findById(Long id) throws RuntimeException {
        if (id == 0) {
            throw new RuntimeException("Genre id can't be empty!");
        }
        try {
            return repository.findById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not find genre with id = " + id);
        }
    }

    @Override
    public List<Genre> findAll() throws RuntimeException {
        var list = repository.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Genres list is empty.");
        }
        return list;
    }

    @Override
    public long count() {
        return repository.count();
    }
}
