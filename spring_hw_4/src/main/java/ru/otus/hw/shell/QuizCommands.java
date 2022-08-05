package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.services.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class QuizCommands {

    private final QuizService service;

    @ShellMethod(value = "Starting quiz", key = {"start-quiz", "run"})
    public void startQuiz() {
        service.startQuiz();
    }

}