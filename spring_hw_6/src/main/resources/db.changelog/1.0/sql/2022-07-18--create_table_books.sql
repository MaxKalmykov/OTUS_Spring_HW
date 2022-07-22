--liquibase formatted sql
--changeset Kalmykov:spring_hw_6/2022-07-18--create_table_books splitStatements:false
--validCheckSum: ANY

CREATE TABLE IF NOT EXISTS public.books
(
    id          bigint generated always as identity  not null,
    author_id   bigint not null,
    genre_id    bigint not null,
    name        varchar(1000) not null,

    CONSTRAINT PK_books PRIMARY KEY (id),
    CONSTRAINT U_books_name UNIQUE (author_id, genre_id, name),
    CONSTRAINT FK_books_author FOREIGN KEY (author_id) REFERENCES public.authors(id),
    CONSTRAINT FK_books_genre FOREIGN KEY (genre_id) REFERENCES public.genres(id)
)