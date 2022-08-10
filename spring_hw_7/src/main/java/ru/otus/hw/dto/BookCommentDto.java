package ru.otus.hw.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
@Accessors(chain = true)
public class BookCommentDto {
    private final long id;
    private final String text;
    private final Long bookId;
    private final String bookAuthorName;
    private final String bookGenreName;
    private final String bookName;
    private String book;

    public String getBook() {
        return "ID: " + bookId + "\n" +
                "Genre: " + bookGenreName + "\n" +
                "Author: " + bookAuthorName + "\n" +
                "Name: " + bookName;
    }
}
