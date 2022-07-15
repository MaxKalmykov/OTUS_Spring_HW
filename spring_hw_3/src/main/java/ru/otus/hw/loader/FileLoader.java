package ru.otus.hw.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class FileLoader implements Loader{
    private final String fileName;
    private final String fileExt;
    private final String locale;

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
