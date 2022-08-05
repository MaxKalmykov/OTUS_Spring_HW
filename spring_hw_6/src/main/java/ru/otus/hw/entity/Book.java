package ru.otus.hw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = ru.otus.hw.entity.Author.class)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne(targetEntity = ru.otus.hw.entity.Genre.class)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = BookComment.class, mappedBy = "book", orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BookComment> comments;


}
