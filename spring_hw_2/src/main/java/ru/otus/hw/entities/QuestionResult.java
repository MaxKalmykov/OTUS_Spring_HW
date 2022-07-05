package ru.otus.hw.entities;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionResult {

    private final int questionNumber;
    private final Question question;
    private final String answerText;
    private final boolean answerCorrect;

    public QuestionResult(int questionNumber, Question question, String answerText, boolean answerCorrect) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answerText = answerText;
        this.answerCorrect = answerCorrect;
    }
}
