package ru.otus.hw.mapper;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import ru.otus.hw.entities.Question;
import ru.otus.hw.loader.Loader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CsvMapper implements Mapper {

    private final Loader loader;

    public CsvMapper(Loader loader) {
        this.loader = loader;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questionList;
        InputStream csvStream = loader.getStream();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(csvStream)).withCSVParser(parser).build();

        questionList = new CsvToBeanBuilder<Question>(csvReader).withType(Question.class).build().parse();

        return questionList;
    }

}