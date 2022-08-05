package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.hw.services.QuizService;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApp.class);
    }
}