--liquibase formatted sql
--changeset Kalmykov:spring_hw_6/2022-07-18--create_table_genres splitStatements:false
--validCheckSum: ANY

CREATE TABLE IF NOT EXISTS public.genres
(
    id      bigint generated always as identity    not null,
    name    varchar(1000) not null,

    CONSTRAINT PK_genres PRIMARY KEY (id),
    CONSTRAINT U_genres_name UNIQUE (name)
)