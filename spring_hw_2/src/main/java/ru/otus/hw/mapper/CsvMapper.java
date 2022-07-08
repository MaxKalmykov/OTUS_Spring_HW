package ru.otus.hw.mapper;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.hw.entities.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CsvMapper implements Mapper {
    private InputStream csvStream;

    public CsvMapper(InputStream csvStream) {
        this.csvStream = csvStream;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questionList;
        CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
        CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(csvStream)).withCSVParser(parser).build();

        questionList = new CsvToBeanBuilder<Question>(csvReader).withType(Question.class).build().parse();

        return questionList;
    }

}
