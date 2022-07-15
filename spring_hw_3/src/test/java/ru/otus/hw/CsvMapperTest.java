package ru.otus.hw;

import org.apache.commons.collections4.SetValuedMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.otus.hw.entities.Question;
import ru.otus.hw.mapper.CsvMapper;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@DisplayName("Проверка маппера документа с вопросами")
public class CsvMapperTest {

    @Test
    @DisplayName("Корректный маппинг тестового файла")
    void shouldHaveCorrectMapping() {
        InputStream mockInputStream = new ByteArrayInputStream(";QUESTION_TEXT;ANSWER_1;ANSWER_2;ANSWER_3;ANSWER_4;RIGHT_ANSWER\n;Test question;Test answer1;Test answer2;Test answer3;Test answer4;Test answer2".getBytes());
        CsvMapper csvMapper = new CsvMapper(mockInputStream);
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