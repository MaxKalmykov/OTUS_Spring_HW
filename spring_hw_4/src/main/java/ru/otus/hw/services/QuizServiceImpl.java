package ru.otus.hw.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw.entities.Question;
import ru.otus.hw.entities.QuestionResult;
import ru.otus.hw.entities.Student;
import ru.otus.hw.mapper.Mapper;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class QuizServiceImpl implements QuizService{

    private final Mapper questionsMapper;
    private final int minSuccessAnswersCount;
    private final QuestionService questionService;
    private final QuestionResultService questionResultService;
    private final MessagePrinter messagePrinter;
    private final StudentService studentService;
    private final Scanner scanner;

    public QuizServiceImpl(
            Mapper questionsMapper,
            @Value("${quiz.min-success-answers-count}") int minSuccessAnswersCount,
            QuestionService questionService,
            QuestionResultService questionResultService,
            MessagePrinter messagePrinter,
            StudentService studentService,
            Scanner scanner
    ) {
        this.questionsMapper = questionsMapper;
        this.minSuccessAnswersCount = minSuccessAnswersCount;
        this.questionService = questionService;
        this.questionResultService = questionResultService;
        this.messagePrinter = messagePrinter;
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public void startQuiz() {
        Student student = studentService.initStudent();
        List<Question> questions = questionsMapper.getQuestions();
        StringBuilder s = new StringBuilder();
        System.out.println(messagePrinter.getMessage("quiz.welcome") + " " + student.getLastName() + " " + student.getFirstName());
        System.out.println(messagePrinter.getMessage("quiz.lets"));
        for (int i = 1; i <= questions.size(); i++) {
            Question question = questions.get(i-1);
            System.out.println(messagePrinter.getMessage("quiz.enter-answer"));
            System.out.println(messagePrinter.getMessage("quiz.question-number") + i);
            System.out.println(questionService.printQuestion(question));
            s.delete(0, s.length());
            while (s.toString().trim().length() <= 0) {
                System.out.println(messagePrinter.getMessage("quiz.enter-answer"));
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
                    System.out.println(messagePrinter.getMessage("quiz.incorrect-answer"));
                }
            }
            questionResultService.add(i, question, s.toString());
        }
        System.out.println(printQuizResult());
    }

    private String printQuizResult(){
        List<QuestionResult> quizProcessMap = questionResultService.getResults();
        StringBuilder res = new StringBuilder();
        res.append(messagePrinter.getMessage("quiz.completed")).append("\n");
        res.append(messagePrinter.getMessage("quiz.result")).append("\n");
        for (QuestionResult questionResult : quizProcessMap) {
            Question q = questionResult.getQuestion();
            res.append(messagePrinter.getMessage("quiz.question-number")).append(questionResult.getQuestionNumber()).append("\n");
            res.append("--").append(messagePrinter.getMessage("quiz.right-answer")).append(" ").append(q.getRightAnswer()).append("\n");
            res.append("--").append(messagePrinter.getMessage("quiz.your-answer")).append(" ").append(questionResult.getAnswerText()).append("\n");
            res.append("--").append(messagePrinter.getMessage("quiz.question-result")).append(" ").append(questionResult.isAnswerCorrect() ? messagePrinter.getMessage("quiz.success") : messagePrinter.getMessage("quiz.fail")).append("\n");
        }
        res.append("======================================").append("\n");
        res.append(messagePrinter.getMessage("quiz.result")).append(" ");
        if (quizProcessMap.stream().filter(QuestionResult::isAnswerCorrect).count() >= minSuccessAnswersCount) {
            res.append(messagePrinter.getMessage("quiz.complete.success"));
        } else {
            res.append(messagePrinter.getMessage("quiz.complete-fail"));
        }
        return res.toString();
    }
}