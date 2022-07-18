package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.entity.Author;
import ru.otus.hw.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository dao;

    public Author findById(Long id) throws RuntimeException {
        if (id == 0) {
            throw new RuntimeException("Author id can't be empty!");
        }
        try {
            return dao.findById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not find author with id = " + id);
        }
    }

    public List<Author> findAll() throws RuntimeException {
        var list = dao.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Authors list is empty.");
        }
        return list;
    }

    public long count() {
        return dao.count();
    }

}
