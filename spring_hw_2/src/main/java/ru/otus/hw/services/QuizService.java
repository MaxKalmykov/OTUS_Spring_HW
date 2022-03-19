package ru.otus.hw.services;

import org.jetbrains.annotations.NotNull;
import ru.otus.hw.entities.QuestionResult;
import ru.otus.hw.entities.Question;
import ru.otus.hw.entities.Student;
import ru.otus.hw.mapper.CsvMapper;

import java.util.*;

public class QuizService {

    private List<Question> questions;
    private final CsvMapper csvMapper;

    public QuizService(@NotNull CsvMapper csvMapper) {
        this.csvMapper = csvMapper;
    }

    public void startQuiz(Student student) {
        if (questions == null){
            questions = csvMapper.getQuestions();
        }
        List<QuestionResult> quizProcessMap = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        System.out.println("Welcome to quiz " + student.getLastName() + " " + student.getFirstName());
        System.out.println("Lets do it!");
        for (int i = 1; i <= questions.size(); i++) {
            Question question = questions.get(i-1);
            System.out.println(printQuestion(question, i));
            s.delete(0, s.length());
            while (s.toString().trim().length() <= 0) {
                System.out.println("Enter your answer:");
                s.append(scanner.nextLine());
                if (question.isQuestionTypeWithAnswerOption()) {
                    try{
                        if (Integer.parseInt(s.toString()) > question.getAnswerList().size() || Integer.parseInt(s.toString()) <= 0) {
                            s.delete(0, s.length());
                        }
                    }
                    catch (NumberFormatException | InputMismatchException e){
                        s.delete(0, s.length());
                    }
                }
                if (s.length() == 0){
                    System.out.println("Incorrect answer! Try again.");
                }
            }
            quizProcessMap.add(new QuestionResult(i, question, s.toString()));
        }
        System.out.println(printQuizResult(quizProcessMap));
    }

    private String printQuizResult(List<QuestionResult> quizProcessMap){
        StringBuilder res = new StringBuilder();
        res.append("Quiz completed.").append("\n");
        res.append("Quiz result:").append("\n");
        for (int i = 0; i < quizProcessMap.size(); i++) {
            QuestionResult questionResult = quizProcessMap.get(i);
            Question q = questionResult.getQuestion();
            res.append("Question №" + questionResult.getQuestionNumber()).append("\n");
            res.append("--Right answer is: " + q.getRightAnswer()).append("\n");
            res.append("--Your answer is: " + questionResult.getStudentAnswerText()).append("\n");
            res.append("--Question result is: " + (questionResult.isAnswerCorrect() ? "SUCCESS" : "FAIL")).append("\n");
        }
        res.append("======================================").append("\n");
        res.append("Quiz result is: ");
        if (quizProcessMap.stream().filter(e -> e.isAnswerCorrect()).count() >= 4) {
            res.append("SUCCESS! Congratulations!");
        } else {
            res.append("FAIL, sorry :'(");
        }
        return res.toString();
    }

    private @NotNull String printQuestion(Question question, int questionNumber){
        StringBuilder res = new StringBuilder();
        List<String> answers = question.getAnswerList();
        res.append("Question №").append(questionNumber).append("\n");
        res.append("Question text: ").append(question.getText()).append("\n");

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
