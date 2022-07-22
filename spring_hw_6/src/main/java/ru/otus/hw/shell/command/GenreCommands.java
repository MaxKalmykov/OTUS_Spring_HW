package ru.otus.hw.shell.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BorderStyle;
import ru.otus.hw.service.GenreService;
import ru.otus.hw.shell.context.AppContext;
import ru.otus.hw.shell.util.ShellUtils;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreService service;
    private final ShellUtils utils;
    private final AppContext context;

    @ShellMethod(value = "Return genres count", key = {"genre-get-count"})
    @ShellMethodAvailability("isAvailable")
    public String count() {
        return "Genres count: " + service.count();
    }

    @ShellMethod(value = "Return table with all genres", key = {"genre-get-all"})
    @ShellMethodAvailability("isAvailable")
    public String findAll() {
        return "Genres list: \n" + utils.renderTable(service.findAll(), BorderStyle.oldschool, "id", "name");
    }

    @ShellMethod(value = "Get genre by ID", key = {"genre-get"})
    @ShellMethodAvailability("isAvailable")
    public String getGenre(@ShellOption(defaultValue = "0") Long id){
        return "Genre: \n" + utils.renderTable(List.of(service.findById(id)), BorderStyle.oldschool, "name");
    }

    public Availability isAvailable() {
        if (context.isFreeContext()) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are in " + context.getState() + " mode. Save data or go to main menu.");
        }
    }

}
