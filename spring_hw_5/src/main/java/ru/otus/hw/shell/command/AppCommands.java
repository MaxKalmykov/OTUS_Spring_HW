package ru.otus.hw.shell.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.shell.context.AppContext;

@ShellComponent
@RequiredArgsConstructor
public class AppCommands {

    private final AppContext context;

    @ShellMethod(value = "Go to main menu", key = {"main-menu"})
    public String free() {
        context.free();
        return "Exited to main menu. Unsaved data is gone :'(";
    }

}
