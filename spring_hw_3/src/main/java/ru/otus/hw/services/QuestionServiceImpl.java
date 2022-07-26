package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.entities.Question;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final MessagePrinter messagePrinter;

    @Override
    public String printQuestion(Question question){
        StringBuilder res = new StringBuilder();
        List<String> answers = question.getAnswerList();
        if (answers == null) {
            answers = new ArrayList<>();
        }
        res.append(messagePrinter.getMessage("question.text")).append(" ").append(question.getText()).append("\r\n");

        if (answers.size() == 0){
            res.append(messagePrinter.getMessage("question.no-possible-answers")).append("\r\n");
        }
        else {
            res.append(messagePrinter.getMessage("question.response-options")).append("\r\n");
            for (int i = 0; i < answers.size(); i++) {
                int answerNum = i + 1;
                res.append(answerNum).append(") ").append(answers.get(i)).append("\r\n");
            }
            res.append(messagePrinter.getMessage("question.answer-number")).append("\r\n");
        }
        return res.toString();
    }
}