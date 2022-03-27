package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.config.AppConfig;
import ru.otus.hw.loader.FileLoader;
import ru.otus.hw.services.QuizService;
import ru.otus.hw.services.StudentService;

import java.io.InputStream;
import java.util.Scanner;

@ComponentScan
public class MainApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuizService service = context.getBean(QuizService.class);
        StudentService studentService = context.getBean(StudentService.class);
        service.startQuiz(studentService.initStudent());
    }
}
