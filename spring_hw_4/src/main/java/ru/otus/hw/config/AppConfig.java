package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Scanner;

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
    public Scanner scanner(){
        return new Scanner(System.in);
    }

}