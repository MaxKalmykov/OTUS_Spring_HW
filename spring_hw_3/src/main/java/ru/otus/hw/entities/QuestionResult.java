package ru.otus.hw.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class QuestionResult {

    private final int questionNumber;
    private final Question question;
    private final String answerText;
    private final boolean answerCorrect;

}