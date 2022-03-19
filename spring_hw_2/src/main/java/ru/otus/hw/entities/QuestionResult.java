package ru.otus.hw.entities;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionResult {

    private int questionNumber;
    private Question question;
    private String studentAnswerText;
    private boolean answerCorrect;

    public QuestionResult(int questionNumber, Question question, String studentAnswer) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.studentAnswerText = setStudentAnswerText(question, studentAnswer);
        this.answerCorrect = setAnswerCorrect();
    }

    private String setStudentAnswerText(Question question, String studentAnswer){
        String answerText;
        if (question.isQuestionTypeWithAnswerOption()) {
            answerText = question.getAnswerList().get(Integer.parseInt(studentAnswer) - 1);
        } else {
            answerText = studentAnswer;
        }

        return answerText;
    }

    private boolean setAnswerCorrect(){
        return studentAnswerText.toLowerCase().equals(question.getRightAnswer().toLowerCase());
    }
}
