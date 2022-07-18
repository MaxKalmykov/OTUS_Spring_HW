package ru.otus.hw.config;

import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import ru.otus.hw.shell.util.ShellUtils;

@Configuration
public class ShellConfig {
    @Bean
    @Scope("singleton")
    public ShellUtils shellUtils(@Lazy Terminal terminal){
        return new ShellUtils(terminal);
    }
}
