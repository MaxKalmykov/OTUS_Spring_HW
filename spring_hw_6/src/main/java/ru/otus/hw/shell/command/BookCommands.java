package ru.otus.hw.shell.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BorderStyle;
import ru.otus.hw.entity.Author;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.Genre;
import ru.otus.hw.service.*;
import ru.otus.hw.shell.context.AppContext;
import ru.otus.hw.shell.context.AppState;
import ru.otus.hw.shell.util.ShellUtils;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final CrudService<Book> service;
    private final NoCrudService<Genre> genreService;
    private final NoCrudService<Author> authorService;
    private final ShellUtils utils;
    private final AppContext context;

    @ShellMethod(value = "Save book", key = {"book-save"})
    @ShellMethodAvailability("isBookMode")
    public String save() {
        Book book = context.getContextObject(Book.class);
        service.save(book);
        context.free();
        return "Book [" + book.getName() + "] saved successfully!";
    }

    @ShellMethod(value = "Edit book", key = {"book-edit"})
    @ShellMethodAvailability("isAvailable")
    public String edit(@ShellOption(defaultValue = "0") Long id) {
        utils.print(getBook(id));
        Book book = service.findById(id);
        utils.print("Activated " + AppState.BOOK_EDIT + " mode.");
        context.setContext(book, AppState.BOOK_EDIT);
        return "Change book parameters and save.";
    }

    @ShellMethod(value = "Set book name", key = {"book-set-name"})
    @ShellMethodAvailability("isBookMode")
    public String setBookName(@ShellOption(defaultValue = "") String name) {
        Book book = context.getContextObject(Book.class);
        book.setName(name);
        return "OK!";
    }

    @ShellMethod(value = "Set book author", key = {"book-set-author"})
    @ShellMethodAvailability("isBookMode")
    public String setBookAuthor(@ShellOption(defaultValue = "0") Long id) {
        Author author = authorService.findById(id);
        Book book = context.getContextObject(Book.class);
        book.setAuthor(author);
        return "OK!";
    }

    @ShellMethod(value = "Set book genre", key = {"book-set-genre"})
    @ShellMethodAvailability("isBookMode")
    public String setBookGenre(@ShellOption(defaultValue = "0") Long id) {
        Genre genre = genreService.findById(id);
        Book book = context.getContextObject(Book.class);
        book.setGenre(genre);
        return "OK!";
    }


    @ShellMethod(value = "Add book", key = {"book-add"})
    @ShellMethodAvailability("isAvailable")
    public String add() {
        utils.print("Activated " + AppState.BOOK_ADD + " mode.");
        context.setContext(new Book(), AppState.BOOK_ADD);
        return "Complete book parameters and save.";
    }

    @ShellMethod(value = "Get Book by ID", key = {"book-get"})
    @ShellMethodAvailability("isAvailable")
    public String getBook(@ShellOption(defaultValue = "0") Long id){
        return "Book: \n" + utils.renderTable(List.of(service.findById(id)), BorderStyle.oldschool, "name", "author", "genre", "comments");
    }

    @ShellMethod(value = "Remove Book by ID", key = {"book-remove"})
    @ShellMethodAvailability("isAvailable")
    public String removeBook(@ShellOption(defaultValue = "0") Long id) {
        service.removeById(id);
        return "Book removed successfully";
    }

    @ShellMethod(value = "Return books count", key = {"book-get-count"})
    @ShellMethodAvailability("isAvailable")
    public String count() {
        return "Books count: " + service.count();
    }

    @ShellMethod(value = "Return table with all books", key = {"book-get-all"})
    @ShellMethodAvailability("isAvailable")
    public String findAll() {
        return "Books list: \n" + utils.renderTable(service.findAll(), BorderStyle.oldschool, "id", "name");
    }

    public Availability isAvailable() {
        if (context.isFreeContext()) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are in " + context.getState() + " mode. Save data or go to main menu.");
        }
    }

    public Availability isBookMode(){
        if (context.getState() == AppState.BOOK_ADD || context.getState() == AppState.BOOK_EDIT) {
            return Availability.available();
        } else {
            return Availability.unavailable("You need to go in " + AppState.BOOK_ADD + " or " + AppState.BOOK_EDIT + " mode.");
        }
    }

}
