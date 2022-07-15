package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;

import java.util.Locale;

@RequiredArgsConstructor
public class MessagePrinter {

    private final MessageSource messageSource;
    private final String locale;

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.forLanguageTag(locale));
    }
}
