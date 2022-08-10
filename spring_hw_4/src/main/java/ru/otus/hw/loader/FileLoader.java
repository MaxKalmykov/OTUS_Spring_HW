package ru.otus.hw.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileLoader implements Loader{
    private final String fileName;
    private final String fileExt;
    private final String locale;

    public FileLoader(
            @Value("questions") String fileName,
            @Value("csv") String fileExt,
            @Value("${quiz.locale}") String locale
    ) {
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.locale = locale;
    }

    @Override
    public InputStream getStream() {
        try {
            return new ClassPathResource(fileName + "_" + locale + "." + fileExt).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
