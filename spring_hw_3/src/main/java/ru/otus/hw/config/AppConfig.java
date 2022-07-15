package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.hw.loader.FileLoader;
import ru.otus.hw.loader.Loader;
import ru.otus.hw.mapper.CsvMapper;
import ru.otus.hw.mapper.Mapper;
import ru.otus.hw.services.MessagePrinter;
import ru.otus.hw.services.QuestionResultService;
import ru.otus.hw.services.QuestionService;
import ru.otus.hw.services.QuizServiceImpl;

@Configuration
@EnableConfigurationProperties(ConfigurationProperties.class)
@ComponentScan("ru.otus.hw")
public class AppConfig {

    //TODO: Никак не хочет перебивать настройки MessageSource из автоконфигурации настройками из yml, поэтому пришлось самому создавать бин
    @Bean
    public MessageSource messageSource(@Value("${spring.messages.basename}") String basename,
                                       @Value("${spring.messages.encoding}") String encoding) {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding(encoding);
        return messageSource;
    }

    @Bean
    public MessagePrinter messagePrinter(MessageSource messageSource, @Value("${quiz.locale}") String locale) {
        return new MessagePrinter(messageSource, locale);
    }

    @Bean
    public Loader csvLoader(@Value("questions") String fileName, @Value("csv") String fileExt, @Value("${quiz.locale}") String locale) { return new FileLoader(fileName, fileExt, locale); }

    @Bean
    public Mapper csvMapper(Loader csvLoader){
        return new CsvMapper(csvLoader.getStream());
    }

    @Bean
    public QuizServiceImpl quizService(Mapper csvMapper,
                                       @Value("${quiz.min-success-answers-count}") int successCount,
                                       QuestionService questionService,
                                       QuestionResultService questionResultService,
                                       MessagePrinter messagePrinter) {
        return new QuizServiceImpl(csvMapper.getQuestions(), successCount, questionService, questionResultService, messagePrinter);
    }
}