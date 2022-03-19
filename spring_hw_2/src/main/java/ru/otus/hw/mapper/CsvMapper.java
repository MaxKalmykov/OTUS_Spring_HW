package ru.otus.hw.mapper;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import ru.otus.hw.entities.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {
    private String fileName;

    public CsvMapper(@NotNull String fileName) {
        this.fileName = fileName;
    }

    public List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<>();
        try {
            InputStream fileStream = new ClassPathResource(fileName).getInputStream();
            CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(fileStream)).withCSVParser(parser).build();

            questionList = new CsvToBeanBuilder<Question>(csvReader).withType(Question.class).build().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questionList;
    }

}
