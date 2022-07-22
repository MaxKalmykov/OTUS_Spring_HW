package ru.otus.hw.converter;

import ru.otus.hw.dto.BookCommentDto;
import ru.otus.hw.entity.BookComment;

import java.util.ArrayList;
import java.util.List;

public class BookCommentEntityToDtoConverter {
    public static List<BookCommentDto> convert(Iterable<BookComment> entities) {
        List<BookCommentDto> res = new ArrayList<>();
        for (BookComment entity : entities) {
            res.add(new BookCommentDto(
                    entity.getId(),
                    entity.getText(),
                    entity.getBook().getId(),
                    entity.getBook().getAuthor().getName(),
                    entity.getBook().getGenre().getName(),
                    entity.getBook().getName())
            );
        }
        return res;
    }
}
