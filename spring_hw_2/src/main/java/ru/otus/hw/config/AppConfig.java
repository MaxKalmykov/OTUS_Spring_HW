package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.loader.FileLoader;
import ru.otus.hw.loader.Loader;
import ru.otus.hw.mapper.CsvMapper;
import ru.otus.hw.mapper.Mapper;
import ru.otus.hw.services.QuestionResultService;
import ru.otus.hw.services.QuestionService;
import ru.otus.hw.services.QuizService;
import ru.otus.hw.services.StudentService;

@Configuration
@ComponentScan("ru.otus.hw")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value(value = "#{T(java.lang.Integer).parseInt('${quiz.min-success-answers-count}')}")
    private int MIN_SUCCESS_ANSWERS_COUNT;

    @Bean
    public Loader csvLoader(@Value("questions.csv") String fileName) { return new FileLoader(fileName); }

    @Bean
    public Mapper csvMapper(Loader csvLoader){
        return new CsvMapper(csvLoader.getStream());
    }

    @Bean
    public QuizService quizService(Mapper csvMapper) {
        return new QuizService(csvMapper.getQuestions(), MIN_SUCCESS_ANSWERS_COUNT);
    }
}
