package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;
import ru.otus.hw.repository.BookCommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookCommentService implements ChildService<BookComment> {

    private final BookCommentRepository repository;
    private final BookService bookService;

    @Override
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

    @Override
    public List<BookComment> findAll() throws RuntimeException {
        var list = repository.findAll();
        if (list.size() == 0) {
            throw new RuntimeException("Book comments list is empty.");
        }
        return list;
    }

    @Override
    @Transactional
    public List<BookComment> findAllByParent(Long bookId) throws RuntimeException {
        Book book = bookService.findById(bookId);
        var list = book.getComments();
        if (list.size() == 0) {
            throw new RuntimeException("Book comments list is empty.");
        }
        return list;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
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

    @Override
    @Transactional
    public void removeById(Long id) {
        if (id == 0) {
            throw new RuntimeException("Comment id can't be empty!");
        }
        repository.removeById(id);
    }

    @Override
    @Transactional
    public void removeAllByParent(Long bookId) throws RuntimeException {
        Book book = bookService.findById(bookId);
        repository.removeAllByBook(book);
    }
}
