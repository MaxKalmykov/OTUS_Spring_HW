DROP TABLE IF EXISTS Authors;
CREATE TABLE Authors(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS Genres;
CREATE TABLE Genres(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS Books;
CREATE TABLE Books(ID BIGINT AUTO_INCREMENT PRIMARY KEY, GenreId BIGINT NOT NULL, AuthorId BIGINT NOT NULL, NAME VARCHAR(255));
