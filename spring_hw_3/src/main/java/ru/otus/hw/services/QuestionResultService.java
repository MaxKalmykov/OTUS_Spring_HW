package ru.otus.hw.services;

import ru.otus.hw.entities.Question;
import ru.otus.hw.entities.QuestionResult;

import java.util.List;

public interface QuestionResultService {
    void add(int questionNumber, Question question, String answer);
    List<QuestionResult> getResults();
}
