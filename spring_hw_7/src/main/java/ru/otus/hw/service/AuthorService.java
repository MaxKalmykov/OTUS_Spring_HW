package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.entity.Author;
import ru.otus.hw.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements NoCrudService<Author> {

    private final AuthorRepository repository;

    @Override
    public Author findById(Long id) throws RuntimeException {
        if (id == 0) {
            throw new RuntimeException("Author id can't be empty!");
        }
        try {
            return repository.findById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not find author with id = " + id);
        }
    }

    @Override
    public List<Author> findAll() throws RuntimeException {
        var list = repository.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Authors list is empty.");
        }
        return list;
    }

    @Override
    public long count() {
        return repository.count();
    }

}
