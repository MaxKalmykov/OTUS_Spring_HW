package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.mapper.CsvMapper;
import ru.otus.hw.services.QuizService;
import ru.otus.hw.services.StudentService;

@Configuration
public class AppConfig {

    @Bean
    public CsvMapper csvMapper(@Value("questions.csv") String fileName){
        return new CsvMapper(fileName);
    }

    @Bean
    public QuizService quizService(CsvMapper csvMapper) {
        return new QuizService(csvMapper);
    };

    @Bean
    public StudentService studentService() {
        return new StudentService();
    }
}
