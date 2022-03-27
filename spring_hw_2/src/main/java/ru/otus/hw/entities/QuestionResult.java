package ru.otus.hw.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionResult {

    private int questionNumber;
    private Question question;
    private String answerText;
    private boolean answerCorrect;

    public QuestionResult(int questionNumber, Question question, String answerText, boolean answerCorrect) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answerText = answerText;
        this.answerCorrect = answerCorrect;
    }
}
