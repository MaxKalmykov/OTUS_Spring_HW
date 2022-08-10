package ru.otus.hw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.entities.Question;
import ru.otus.hw.loader.FileLoader;
import ru.otus.hw.mapper.CsvMapper;

import java.util.List;

@DisplayName("Проверка маппера документа с вопросами")
public class CsvMapperTest {

    FileLoader loader = new FileLoader("questions-mock", "csv", "en");

    @Test
    @DisplayName("Корректный маппинг тестового файла")
    void shouldHaveCorrectMapping() {
        CsvMapper csvMapper = new CsvMapper(loader);
        List<Question> questions = csvMapper.getQuestions();
        Question question = questions.get(0);
        List<String> answers = question.getAnswerList();
        Assertions.assertEquals("Test question", question.getText());
        Assertions.assertEquals("Test answer4", answers.get(0));
        Assertions.assertEquals("Test answer3", answers.get(1));
        Assertions.assertEquals("Test answer2", answers.get(2));
        Assertions.assertEquals("Test answer1", answers.get(3));
        Assertions.assertEquals("Test answer2", question.getRightAnswer());
    }


}