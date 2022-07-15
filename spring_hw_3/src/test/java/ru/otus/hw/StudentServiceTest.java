package ru.otus.hw;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.entities.Student;
import ru.otus.hw.services.MessagePrinter;
import ru.otus.hw.services.StudentServiceImpl;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@SpringBootTest
@Import(AppConfig.class)
@DisplayName("Проверка сервиса инициализации студента")
public class StudentServiceTest {

    private InputStream mockInputStream;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @Autowired
    private MessageSource messageSource;

    private MessagePrinter messagePrinter;

    @PostConstruct
    void init() {
        messagePrinter = new MessagePrinter(messageSource, "en");
    }

    @Test
    @DisplayName("Корректный запрос данных студента")
    void shouldHaveCorrectInitStudentData() {
        mockInputStream = new ByteArrayInputStream(" \nTest\n\nTestich".getBytes());
        System.setIn(mockInputStream);
        System.setOut(new PrintStream(outContent));
        new StudentServiceImpl(messagePrinter).initStudent();
        Assertions.assertEquals("Enter your last name:" + System.lineSeparator() +
                                        "Incorrect last name! Try again." + System.lineSeparator() +
                                        "Enter your first name:" + System.lineSeparator() +
                                        "Incorrect first name! Try again." + System.lineSeparator(), outContent.toString());
    }

    @Test
    @DisplayName("Корректная инициализация студента")
    void shouldHaveCorrectInitStudent() {
        mockInputStream = new ByteArrayInputStream("Testich\nTest".getBytes());
        System.setIn(mockInputStream);
        Student student = new StudentServiceImpl(messagePrinter).initStudent();
        Assertions.assertEquals("Testich", student.getLastName());
        Assertions.assertEquals("Test", student.getFirstName());
    }

    @After
    public void clearSystemIn(){
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

}
