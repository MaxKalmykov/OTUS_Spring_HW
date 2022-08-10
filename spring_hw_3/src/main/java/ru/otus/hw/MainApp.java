package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw.services.QuizService;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        var context = SpringApplication.run(MainApp.class, args);
        QuizService service = context.getBean(QuizService.class);
        service.startQuiz();
    }
}