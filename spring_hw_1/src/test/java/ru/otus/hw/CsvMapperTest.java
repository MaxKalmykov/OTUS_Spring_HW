package ru.otus.hw;

import org.apache.commons.collections4.SetValuedMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.entities.Question;
import ru.otus.hw.mapper.CsvMapper;
import org.junit.jupiter.api.Assertions;

import java.util.List;

@DisplayName("Проверка парсера документа с вопросами")
public class CsvMapperTest {

    @Test
    @DisplayName("Корректный парсинг тестового файла")
    void shouldHaveCorrectParse() {
        CsvMapper csvMapper = new CsvMapper("questions-mock.csv");
        List<Question> questions = csvMapper.getQuestions();
        Question question = questions.get(0);
        SetValuedMap<String, String> answers = question.getAnswers();

        Assertions.assertEquals("Test question", question.getText());
        Assertions.assertEquals("[Test answer1]", answers.get("ANSWER_1").toString());
        Assertions.assertEquals("[Test answer2]", answers.get("ANSWER_2").toString());
        Assertions.assertEquals("[Test answer3]", answers.get("ANSWER_3").toString());
        Assertions.assertEquals("[Test answer4]", answers.get("ANSWER_4").toString());
        Assertions.assertEquals("Test answer2", question.getRightAnswer());
    }


}
