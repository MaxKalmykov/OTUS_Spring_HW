package ru.otus.hw.services;

import org.jetbrains.annotations.NotNull;
import ru.otus.hw.entities.Question;
import ru.otus.hw.mapper.CsvMapper;

import java.util.ArrayList;
import java.util.List;

public class QuizService {

    private final List<Question> questions;

    public QuizService(@NotNull CsvMapper csvMapper) {
        this.questions = csvMapper.getQuestions();
    }

    public void startQuiz() {
        for (int i = 1; i <= questions.size(); i++) {
            System.out.println(printQuestion(i));
        }
    }

    private @NotNull String printQuestion(int questionNum){
        if (questionNum < 1) {
            throw new RuntimeException("The question number cannot be less than 1.");
        }
        if (questionNum > questions.size()) {
            throw new RuntimeException("There is no question with this number in the question file.");
        }
        Question q = questions.get(questionNum - 1);
        StringBuilder res = new StringBuilder();
        List<String> answers = new ArrayList<>();
        for (String e : q.getAnswers().values()) {
            if (e.trim().length() > 0) {
                answers.add(e);
            }
        }
        res.append("Question №").append(questionNum).append("\n");
        res.append("Question text: ").append(q.getText()).append("\n");

        if (answers.size() == 0){
            res.append("A question with no possible answers, the answer needs to be entered yourself." + "\n");
        }
        else {
            res.append("Response options:" + "\n");
            for (int i = 0; i < answers.size(); i++) {
                int answerNum = i + 1;
                res.append(answerNum).append(") ").append(answers.get(i)).append("\n");
            }
            res.append("Enter the correct answer number." + "\n");
        }
        return res.toString();
    }
}
