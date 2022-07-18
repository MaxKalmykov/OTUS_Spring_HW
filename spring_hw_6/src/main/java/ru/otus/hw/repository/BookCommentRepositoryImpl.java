package ru.otus.hw.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookCommentRepositoryImpl implements BookCommentRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(BookComment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    @Override
    public List<BookComment> findAll() {
        return em.createQuery("select bc from BookComment bc join fetch bc.book", BookComment.class).getResultList();
    }

    @Override
    public Optional<BookComment> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return em.createQuery("select bc from BookComment bc join fetch bc.book where bc.id = :id", BookComment.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public List<BookComment> findAllByBook(Book book) {
        return em.createQuery("select bc from BookComment bc join fetch Book b where b.id = :id", BookComment.class)
                .setParameter("id", book.getId())
                .getResultList();
    }

    @Override
    public long count() {
        Long cnt = em.createQuery("select count(bc) from BookComment bc", Long.class).getSingleResult();
        return cnt == null ? 0 : cnt;
    }

    @Override
    public void removeById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public void removeAllByBook(Book book) {
        findAllByBook(book).forEach(em::remove);
    }

    @Override
    public void removeAll() {
        em.createQuery("delete from BookComment").executeUpdate();
    }
}
