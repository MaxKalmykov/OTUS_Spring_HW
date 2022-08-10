package ru.otus.hw.shell.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BorderStyle;
import ru.otus.hw.converter.BookCommentEntityToDtoConverter;
import ru.otus.hw.entity.Book;
import ru.otus.hw.entity.BookComment;
import ru.otus.hw.service.BookCommentService;
import ru.otus.hw.service.BookService;
import ru.otus.hw.service.ChildService;
import ru.otus.hw.service.NoCrudService;
import ru.otus.hw.shell.context.AppContext;
import ru.otus.hw.shell.context.AppState;
import ru.otus.hw.shell.util.ShellUtils;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookCommentCommands {

    private final ChildService<BookComment> service;
    private final NoCrudService<Book> bookService;
    private final ShellUtils utils;
    private final AppContext context;

    @ShellMethod(value = "Save book comment", key = {"comment-save"})
    @ShellMethodAvailability("isCommentMode")
    public String save() {
        BookComment comment = context.getContextObject(BookComment.class);
        try {
            service.save(comment);
        } finally {
            context.free();
        }
        return "Comment for book [" + comment.getBook().getName() + "] saved successfully!";
    }

    @ShellMethod(value = "Edit book comment", key = {"comment-edit"})
    @ShellMethodAvailability("isAvailable")
    public String edit(@ShellOption(defaultValue = "0") Long id) {
        utils.print(getComment(id));
        BookComment comment = service.findById(id);
        utils.print("Activated " + AppState.COMMENT_EDIT + " mode.");
        context.setContext(comment, AppState.COMMENT_EDIT);
        return "Change comment parameters and save.";
    }

    @ShellMethod(value = "Set book comment text", key = {"comment-set-text"})
    @ShellMethodAvailability("isCommentMode")
    public String setCommentText(@ShellOption(defaultValue = "") String text) {
        BookComment comment = context.getContextObject(BookComment.class);
        comment.setText(text);
        return "OK!";
    }

    @ShellMethod(value = "Set book for comment", key = {"comment-set-book"})
    @ShellMethodAvailability("isCommentMode")
    public String setCommentBook(@ShellOption(defaultValue = "0") Long id) {
        Book book = bookService.findById(id);
        BookComment comment = context.getContextObject(BookComment.class);
        comment.setBook(book);
        return "OK!";
    }

    @ShellMethod(value = "Add book comment", key = {"comment-add"})
    @ShellMethodAvailability("isAvailable")
    public String add() {
        utils.print("Activated " + AppState.COMMENT_ADD + " mode.");
        context.setContext(new BookComment(), AppState.COMMENT_ADD);
        return "Complete comment parameters and save.";
    }

    @ShellMethod(value = "Get book comment by ID", key = {"comment-get"})
    @ShellMethodAvailability("isAvailable")
    public String getComment(@ShellOption(defaultValue = "0") Long id){
        return "Book comment: \n" + utils.renderTable(BookCommentEntityToDtoConverter.convert(List.of(service.findById(id))), BorderStyle.oldschool, "book", "text");
    }

    @ShellMethod(value = "Get book comment by ID", key = {"comment-get-by-book"})
    @ShellMethodAvailability("isAvailable")
    public String getCommentsByBook(@ShellOption(defaultValue = "0") Long id){
        return "Book comments: \n" + utils.renderTable(BookCommentEntityToDtoConverter.convert(service.findAllByParent(id)), BorderStyle.oldschool, "text");
    }

    @ShellMethod(value = "Remove book comment by ID", key = {"comment-remove"})
    @ShellMethodAvailability("isAvailable")
    public String removeComment(@ShellOption(defaultValue = "0") Long id) {
        service.removeById(id);
        return "Book comment removed successfully";
    }

    @ShellMethod(value = "Get book comment by ID", key = {"comment-remove-by-book"})
    @ShellMethodAvailability("isAvailable")
    public String removeCommentsByBook(@ShellOption(defaultValue = "0") Long id){
        service.removeAllByParent(id);
        return "Book comments removed successfully!";
    }

    @ShellMethod(value = "Return comments count for all books", key = {"comment-get-count"})
    @ShellMethodAvailability("isAvailable")
    public String count() {
        return "Books comments count: " + service.count();
    }

    @ShellMethod(value = "Return table with all comments for all books", key = {"comment-get-all"})
    @ShellMethodAvailability("isAvailable")
    public String findAll() {
        return "Books comments list: \n" + utils.renderTable(BookCommentEntityToDtoConverter.convert(service.findAll()), BorderStyle.oldschool, "id", "book", "text");
    }

    public Availability isAvailable() {
        if (context.isFreeContext()) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are in " + context.getState() + " mode. Save data or go to main menu.");
        }
    }

    public Availability isCommentMode(){
        if (context.getState() == AppState.COMMENT_ADD || context.getState() == AppState.COMMENT_EDIT) {
            return Availability.available();
        } else {
            return Availability.unavailable("You need to go in " + AppState.COMMENT_ADD + " or " + AppState.COMMENT_EDIT + " mode.");
        }
    }

}
