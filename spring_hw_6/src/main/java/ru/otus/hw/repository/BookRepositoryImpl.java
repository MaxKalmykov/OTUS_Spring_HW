package ru.otus.hw.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre where b.id = :id", Book.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class).getResultList();
    }

    @Override
    public long count() {
        Long cnt = em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
        return cnt == null ? 0 : cnt;
    }

    @Override
    public void removeById(Long id) {
        findById(id).ifPresent(em::remove);
    }
}
