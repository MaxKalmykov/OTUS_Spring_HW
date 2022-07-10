package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.GenreDao;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao dao;

    public Genre findById(Long id) throws RuntimeException {
        if (id == 0) {
            throw new RuntimeException("Genre id can't be empty!");
        }
        try {
            return dao.findById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not find genre with id = " + id);
        }
    }

    public List<Genre> findAll() throws RuntimeException {
        var list = dao.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Genres list is empty.");
        }
        return list;
    }

    public long count() {
        return dao.count();
    }
}
