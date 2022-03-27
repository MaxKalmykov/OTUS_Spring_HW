package ru.otus.hw.mapper;

import ru.otus.hw.entities.Question;

import java.io.InputStream;
import java.util.List;

public interface Mapper {
    List<Question> getQuestions();
}
