package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.loader.FileLoader;
import ru.otus.hw.mapper.CsvMapper;
import ru.otus.hw.services.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Проверка корректного формирования теста")
public class QuizServiceTest {

    private InputStream mockIn;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Autowired
    private MessageSource messageSource;

    private MessagePrinter messagePrinter;
    private QuestionService questionService;
    private FileLoader loader;

    @PostConstruct
    void init() {
        messagePrinter = new MessagePrinter(messageSource, "en");
        loader = new FileLoader("questions-mock", "csv", "en");
        questionService = new QuestionServiceImpl(messagePrinter);
    }

    @Test
    @DisplayName("Корректный текст теста c успешным прохождением")
    void shouldHaveCorrectTest(){
        String successRes = "Enter your last name:" + System.lineSeparator() +
                "Enter your first name:" + System.lineSeparator() +
                "Welcome to quiz Petrov Ivan" + System.lineSeparator() +
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

        mockIn = new ByteArrayInputStream("Petrov\nIvan\n3\n".getBytes());
        Scanner scanner = new Scanner(mockIn);
        StudentService studentService = new StudentServiceImpl(scanner, messagePrinter);
        CsvMapper csvMapper = new CsvMapper(loader);
        QuizServiceImpl quizService = new QuizServiceImpl(csvMapper, 1, questionService, new QuestionResultServiceImpl(), messagePrinter, studentService, scanner);
        System.setOut(new PrintStream(outContent));
        quizService.startQuiz();
        Assertions.assertEquals(successRes.replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));

    }

    @Test
    @DisplayName("Корректный текст теста без успешного прохождения")
    void shouldHaveCorrectTestFail(){
        String successRes = "Enter your last name:" + System.lineSeparator() +
                "Enter your first name:" + System.lineSeparator() +
                "Welcome to quiz Petrov Ivan" + System.lineSeparator() +
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

        mockIn = new ByteArrayInputStream("Petrov\nIvan\n2\n".getBytes());
        Scanner scanner = new Scanner(mockIn);
        StudentService studentService = new StudentServiceImpl(scanner, messagePrinter);
        System.setIn(mockIn);
        CsvMapper csvMapper = new CsvMapper(loader);
        QuizServiceImpl quizService = new QuizServiceImpl(csvMapper, 1, questionService, new QuestionResultServiceImpl(), messagePrinter, studentService, scanner);
        System.setOut(new PrintStream(outContent));
        quizService.startQuiz();
        Assertions.assertEquals(successRes.replaceAll("\n", "").replaceAll("\r", ""), outContent.toString().replaceAll("\n", "").replaceAll("\r", ""));

    }
}
