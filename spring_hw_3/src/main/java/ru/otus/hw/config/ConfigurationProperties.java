package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "quiz")
@Component
@Getter
@Setter
public class ConfigurationProperties {

    private int minSuccessAnswersCount;
    private String local;

}