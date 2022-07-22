--liquibase formatted sql
--changeset Kalmykov:spring_hw_6/2022-07-18--insert_into_genres splitStatements:false
--validCheckSum: ANY

INSERT INTO public.genres
(
 id,
 name
)
VALUES
(
 1,
 'DefaultGenre'
);