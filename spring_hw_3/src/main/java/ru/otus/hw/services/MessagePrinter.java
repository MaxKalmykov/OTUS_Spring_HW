package ru.otus.hw.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessagePrinter {

    private final MessageSource messageSource;
    private final String locale;

    public MessagePrinter(
            MessageSource messageSource,
            @Value("${quiz.locale}") String locale
    ) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.forLanguageTag(locale));
    }
}
