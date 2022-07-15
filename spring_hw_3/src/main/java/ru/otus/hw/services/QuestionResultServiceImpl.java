package ru.otus.hw.services;

import org.springframework.stereotype.Service;
import ru.otus.hw.entities.Question;
import ru.otus.hw.entities.QuestionResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionResultServiceImpl implements QuestionResultService {
    private final List<QuestionResult> questionResultList = new ArrayList<>();

    public void add(int questionNumber, Question question, String answer){
        String answerText = setAnswerText(question, answer);
        boolean answerCorrect = validateAnswer(question, answerText);
        questionResultList.add(new QuestionResult(questionNumber, question, answerText, answerCorrect));
    }

    public List<QuestionResult> getResults(){
        return questionResultList;
    }

    private String setAnswerText(Question question, String answer){
        String answerText;
        if (question.isQuestionTypeWithAnswerOption()) {
            answerText = question.getAnswerList().get(Integer.parseInt(answer) - 1);
        } else {
            answerText = answer;
        }

        return answerText;
    }

    private boolean validateAnswer(Question question, String answerText){
        return answerText.equalsIgnoreCase(question.getRightAnswer());
    }

}