package ru.otus.hw;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.entities.Question;
import ru.otus.hw.entities.Student;
import ru.otus.hw.mapper.CsvMapper;
import ru.otus.hw.services.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

@SpringBootTest
@Import(AppConfig.class)
@DisplayName("Проверка корректного формирования теста")
public class QuizServiceTest {

    private InputStream mockFileStream;
    private InputStream mockIn;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @Autowired
    private MessageSource messageSource;

    private MessagePrinter messagePrinter;
    private QuestionService questionService;

    @PostConstruct
    void init() {
        messagePrinter = new MessagePrinter(messageSource, "en");
        questionService = new QuestionServiceImpl(messagePrinter);
    }

    @Before
    public void initSystemInOut(){
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Корректный текст теста c успешным прохождением")
    void shouldHaveCorrectTest(){
        String successRes = "Welcome to quiz Petrov Ivan" + System.lineSeparator() +
                "Let's do it!" + System.lineSeparator() +
                "Enter your answer:" + System.lineSeparator() +
                "Question number:1" + System.lineSeparator() +
                "Question text: Test question" + System.lineSeparator() +
                "Response options:" + System.lineSeparator() +
                "1) Test answer4" + System.lineSeparator() +
                "2) Test answer3" + System.lineSeparator() +
                "3) Test answer2" + System.lineSeparator() +
                "4) Test answer1" + System.lineSeparator() +
                "Enter the correct answer number." + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Enter your answer:" + System.lineSeparator() +
                "Quiz completed." + System.lineSeparator() +
                "Quiz result:" + System.lineSeparator() +
                "Question number:1" + System.lineSeparator() +
                "--Right answer is: Test answer2" + System.lineSeparator() +
                "--Your answer is: Test answer2" + System.lineSeparator() +
                "--Question result: SUCCESS" + System.lineSeparator() +
                "======================================" + System.lineSeparator() +
                "Quiz result: SUCCESS! Congratulations!" + System.lineSeparator();

        mockFileStream = new ByteArrayInputStream(";QUESTION_TEXT;ANSWER_1;ANSWER_2;ANSWER_3;ANSWER_4;RIGHT_ANSWER\n;Test question;Test answer1;Test answer2;Test answer3;Test answer4;Test answer2".getBytes());
        mockIn = new ByteArrayInputStream("3".getBytes());
        System.setIn(mockIn);
        CsvMapper csvMapper = new CsvMapper(mockFileStream);
        List<Question> questions = csvMapper.getQuestions();
        Student student = new Student("Petrov", "Ivan");
        QuizServiceImpl quizService = new QuizServiceImpl(questions, 1, questionService, new QuestionResultServiceImpl(), messagePrinter);
        System.setOut(new PrintStream(outContent));
        quizService.startQuiz(student);
        Assertions.assertEquals(successRes.replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));

    }

    @Test
    @DisplayName("Корректный текст теста без успешного прохождения")
    void shouldHaveCorrectTestFail(){
        String successRes = "Welcome to quiz Petrov Ivan" + System.lineSeparator() +
                "Let's do it!" + System.lineSeparator() +
                "Enter your answer:" + System.lineSeparator() +
                "Question number:1" + System.lineSeparator() +
                "Question text: Test question" + System.lineSeparator() +
                "Response options:" + System.lineSeparator() +
                "1) Test answer4" + System.lineSeparator() +
                "2) Test answer3" + System.lineSeparator() +
                "3) Test answer2" + System.lineSeparator() +
                "4) Test answer1" + System.lineSeparator() +
                "Enter the correct answer number." + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Enter your answer:" + System.lineSeparator() +
                "Quiz completed." + System.lineSeparator() +
                "Quiz result:" + System.lineSeparator() +
                "Question number:1" + System.lineSeparator() +
                "--Right answer is: Test answer2" + System.lineSeparator() +
                "--Your answer is: Test answer3" + System.lineSeparator() +
                "--Question result: FAIL" + System.lineSeparator() +
                "======================================" + System.lineSeparator() +
                "Quiz result: FAIL, sorry :'(" + System.lineSeparator();

        mockFileStream = new ByteArrayInputStream(";QUESTION_TEXT;ANSWER_1;ANSWER_2;ANSWER_3;ANSWER_4;RIGHT_ANSWER\n;Test question;Test answer1;Test answer2;Test answer3;Test answer4;Test answer2".getBytes());
        mockIn = new ByteArrayInputStream("2".getBytes());
        System.setIn(mockIn);
        CsvMapper csvMapper = new CsvMapper(mockFileStream);
        List<Question> questions = csvMapper.getQuestions();
        Student student = new Student("Petrov", "Ivan");
        QuizServiceImpl quizService = new QuizServiceImpl(questions, 1, questionService, new QuestionResultServiceImpl(), messagePrinter);
        System.setOut(new PrintStream(outContent));
        quizService.startQuiz(student);
        Assertions.assertEquals(successRes.replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));

    }

    @After
    public void clearSystemInOut(){
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
}
