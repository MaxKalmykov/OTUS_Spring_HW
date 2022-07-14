package ru.otus.hw.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class Book {
    private Long id;
    private Author Author;
    private Genre Genre;
    private String name;
}
