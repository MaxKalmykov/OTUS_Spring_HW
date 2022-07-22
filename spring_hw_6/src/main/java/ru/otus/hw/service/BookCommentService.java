package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;
import ru.otus.hw.repository.BookCommentRepository;
import ru.otus.hw.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookCommentService {

    private final BookCommentRepository repository;
    private final BookService bookService;

    public BookComment findById(Long id) throws RuntimeException {
        if (id == 0) {
            throw new RuntimeException("Book comment id can't be empty!");
        }
        try {
            return repository.findById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Could not find book comment with id = " + id);
        }
    }

    public List<BookComment> findAll() throws RuntimeException {
        var list = repository.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Book comments list is empty.");
        }
        return list;
    }

    public List<BookComment> findAllByBook(Long bookId) throws RuntimeException {
        Book book = bookService.findById(bookId);
        var list = repository.findAllByBook(book);
        if (list.size() == 0) {
            throw new RuntimeException("Book comments list is empty.");
        }
        return list;
    }

    public long count() {
        return repository.count();
    }

    @Transactional
    public void save(BookComment comment) throws RuntimeException {
        if (comment.getText() == null || Objects.equals(comment.getText(), "")) {
            throw new RuntimeException("Comment text can't be empty");
        }
        if (comment.getBook() == null) {
            throw new RuntimeException("Comment can't be without book");
        }
        repository.save(comment);
    }

    @Transactional
    public void removeById(Long id) {
        if (id == 0) {
            throw new RuntimeException("Comment id can't be empty!");
        }
        repository.removeById(id);
    }

    @Transactional
    public void removeAll() {
        repository.removeAll();
    }

    @Transactional
    public void removeAllByBook(Long bookId) throws RuntimeException {
        Book book = bookService.findById(bookId);
        repository.removeAllByBook(book);
    }
}
