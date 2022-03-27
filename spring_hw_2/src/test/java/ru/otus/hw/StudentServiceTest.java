package ru.otus.hw;

import org.apache.commons.collections4.SetValuedMap;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.entities.Student;
import ru.otus.hw.services.StudentService;

import java.io.*;

@DisplayName("Проверка сервиса инициализации студента")
public class StudentServiceTest {

    private InputStream mockInputStream;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @Test
    @DisplayName("Корректный запрос данных студента")
    void shouldHaveCorrectInitStudentData() {
        mockInputStream = new ByteArrayInputStream(" \nTest\n\nTestich".getBytes());
        System.setIn(mockInputStream);
        System.setOut(new PrintStream(outContent));
        Student student = new StudentService().initStudent();
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
        Student student = new StudentService().initStudent();
        Assertions.assertEquals("Testich", student.getLastName());
        Assertions.assertEquals("Test", student.getFirstName());
    }

    @After
    public void clearSystemIn(){
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

}
