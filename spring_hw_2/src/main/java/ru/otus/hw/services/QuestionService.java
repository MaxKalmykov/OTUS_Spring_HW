package ru.otus.hw.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.hw.entities.Question;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    public @NotNull String printQuestion(Question question){
        StringBuilder res = new StringBuilder();
        List<String> answers = question.getAnswerList();
        if (answers == null) {
            answers = new ArrayList<>();
        }
        res.append("Question text: ").append(question.getText()).append("\r\n");

        if (answers.size() == 0){
            res.append("A question with no possible answers, the answer needs to be entered yourself." + "\r\n");
        }
        else {
            res.append("Response options:" + "\r\n");
            for (int i = 0; i < answers.size(); i++) {
                int answerNum = i + 1;
                res.append(answerNum).append(") ").append(answers.get(i)).append("\r\n");
            }
            res.append("Enter the correct answer number." + "\r\n");
        }
        return res.toString();
    }
}
