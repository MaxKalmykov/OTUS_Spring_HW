--liquibase formatted sql
--changeset Kalmykov:spring_hw_6/2022-07-18--insert_into_book_comments splitStatements:false
--validCheckSum: ANY

INSERT INTO public.book_comments
(
 id,
 book_id,
 text
)
VALUES
(
 1,
 1,
 'DefaultComment'
);