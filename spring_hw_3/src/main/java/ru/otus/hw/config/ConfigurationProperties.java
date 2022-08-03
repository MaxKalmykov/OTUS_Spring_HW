package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "quiz")
@Getter
@Setter
public class ConfigurationProperties {

    private int minSuccessAnswersCount;
    private String local;

}