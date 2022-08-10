--liquibase formatted sql
--changeset Kalmykov:spring_hw_6/2022-07-18--create_table_book_comments splitStatements:false
--validCheckSum: ANY

CREATE TABLE IF NOT EXISTS public.book_comments
(
    id          bigint generated always as identity not null,
    book_id     bigint not null,
    text        text not null,

    CONSTRAINT PK_book_comments PRIMARY KEY (id),
    CONSTRAINT FK_book_comments_book FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE
)