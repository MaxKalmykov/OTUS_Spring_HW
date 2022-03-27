package ru.otus.hw.loader;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class FileLoader implements Loader{
    private String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public InputStream getStream() {
        try {
            return new ClassPathResource(fileName).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
