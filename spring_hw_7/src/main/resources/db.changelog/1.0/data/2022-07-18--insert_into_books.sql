--liquibase formatted sql
--changeset Kalmykov:spring_hw_6/2022-07-18--insert_into_books splitStatements:false
--validCheckSum: ANY

INSERT INTO public.books
(
 id,
 author_id,
 genre_id,
 name
)
VALUES
(
 1,
 1,
 1,
 'DefaultBook'
);