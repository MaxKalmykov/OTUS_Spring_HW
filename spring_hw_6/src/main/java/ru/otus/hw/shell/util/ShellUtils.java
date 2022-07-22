package ru.otus.hw.shell.util;

import lombok.RequiredArgsConstructor;
import org.jline.terminal.Terminal;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.Arrays;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class ShellUtils {
    private final Terminal terminal;

    public void print(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }

    public <T> String renderTable(Iterable<T> data, BorderStyle style, String... header) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Arrays.stream(header).forEach(e -> map.put(e, e.toUpperCase()));
        TableModel model = new BeanListTableModel<>(data, map);
        TableBuilder builder = new TableBuilder(model);
        builder.addFullBorder(style);
        return builder.build().render(80);
    }
}
