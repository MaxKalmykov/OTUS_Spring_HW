package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.entities.QuestionResult;
import ru.otus.hw.entities.Question;
import ru.otus.hw.entities.Student;

import java.util.*;

@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{

    private final List<Question> questions;
    private final int MIN_SUCCESS_ANSWERS_COUNT;
    private final QuestionService questionService;
    private final QuestionResultService questionResultService;
    private final MessagePrinter messagePrinter;

    public void startQuiz(Student student) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        System.out.println("Welcome to quiz " + student.getLastName() + " " + student.getFirstName());
        System.out.println("Let's do it!");
        for (int i = 1; i <= questions.size(); i++) {
            Question question = questions.get(i-1);
            System.out.println("Enter your answer:");
            System.out.println("Question number:" + i);
            System.out.println(questionService.printQuestion(question));
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
            questionResultService.add(i, question, s.toString());
        }
        System.out.println(printQuizResult());
    }

    private String printQuizResult(){
        List<QuestionResult> quizProcessMap = questionResultService.getResults();
        StringBuilder res = new StringBuilder();
        res.append("Quiz completed.").append("\n");
        res.append("Quiz result:").append("\n");
        for (QuestionResult questionResult : quizProcessMap) {
            Question q = questionResult.getQuestion();
            res.append("Question number:").append(questionResult.getQuestionNumber()).append("\n");
            res.append("--Right answer is: ").append(q.getRightAnswer()).append("\n");
            res.append("--Your answer is: ").append(questionResult.getAnswerText()).append("\n");
            res.append("--Question result: ").append(questionResult.isAnswerCorrect() ? "SUCCESS" : "FAIL").append("\n");
        }
        res.append("======================================").append("\n");
        res.append("Quiz result: ");
        if (quizProcessMap.stream().filter(QuestionResult::isAnswerCorrect).count() >= MIN_SUCCESS_ANSWERS_COUNT) {
            res.append("SUCCESS! Congratulations!");
        } else {
            res.append("FAIL, sorry :'(");
        }
        return res.toString();
    }
}