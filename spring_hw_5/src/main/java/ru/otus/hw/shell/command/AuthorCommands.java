package ru.otus.hw.shell.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import ru.otus.hw.service.AuthorService;
import ru.otus.hw.shell.context.AppContext;
import ru.otus.hw.shell.util.ShellUtils;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorService service;
    private final ShellUtils utils;
    private final AppContext context;

    @ShellMethod(value = "Return authors count", key = {"author-get-count"})
    @ShellMethodAvailability("isAvailable")
    public String count() {
        return "Authors count: " + service.count();
    }

    @ShellMethod(value = "Return table with all authors", key = {"author-get-all"})
    @ShellMethodAvailability("isAvailable")
    public String findAll() {
        return "Authors list: \n" + utils.renderTable(service.findAll(), BorderStyle.oldschool, "id", "name");
    }

    @ShellMethod(value = "Get Author by ID", key = {"author-get"})
    @ShellMethodAvailability("isAvailable")
    public String getAuthor(@ShellOption(defaultValue = "0") Long id){
        return "Author: \n" + utils.renderTable(List.of(service.findById(id)), BorderStyle.oldschool, "name");
    }

    public Availability isAvailable() {
        if (context.isFreeContext()) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are in " + context.getState() + " mode. Save data or go to main menu.");
        }
    }
}
