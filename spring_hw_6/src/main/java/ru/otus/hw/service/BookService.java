package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.entity.Book;
import ru.otus.hw.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService implements CrudService<Book> {

    private final BookRepository repository;

    @Override
    public Book findById(Long id) throws RuntimeException {
        if (id == 0) {
            throw new RuntimeException("Book id can't be empty!");
        }
        try {
            return repository.findById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not find book with id = " + id);
        }
    }

    @Override
    public List<Book> findAll() throws RuntimeException {
        var list = repository.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Books list is empty.");
        }
        return list;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void save(Book book) throws RuntimeException {
        if (book.getName() == null || Objects.equals(book.getName(), "")) {
            throw new RuntimeException("Book name can't be empty");
        }
        if (book.getAuthor() == null) {
            throw new RuntimeException("Book author can't be empty");
        }
        if (book.getGenre() == null) {
            throw new RuntimeException("Book genre can't be empty");
        }
        repository.save(book);
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        if (id == 0) {
            throw new RuntimeException("Book id can't be empty!");
        }
        repository.removeById(id);
    }
}
