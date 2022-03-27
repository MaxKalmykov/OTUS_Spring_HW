package ru.otus.hw;

import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.entities.Question;
import ru.otus.hw.services.QuestionService;

@DisplayName("Проверка формирования текста вопроса")
public class QuestionServiceTest {

    @Test
    @DisplayName("Корректный текст вопроса с вариантами ответа")
    void shouldHaveCorrectQuestionTextWithVariant(){
        QuestionService questionService = new QuestionService();
        Question question = new Question();
        SetValuedMap<String, String> answers = new HashSetValuedHashMap<>();
        answers.put("ANSWER_1", "Test1");
        answers.put("ANSWER_2", "Test2");
        answers.put("ANSWER_3", "Test3");
        answers.put("ANSWER_4", "Test4");
        question.setText("Test question");
        question.setAnswers(answers);
        question.setRightAnswer("Test2");
        Assertions.assertEquals("Question text: Test question" + System.lineSeparator() +
                "Response options:" + System.lineSeparator() +
                "1) Test4" + System.lineSeparator() +
                "2) Test3" + System.lineSeparator() +
                "3) Test2" + System.lineSeparator() +
                "4) Test1" + System.lineSeparator() +
                "Enter the correct answer number." + System.lineSeparator(), questionService.printQuestion(question));
    }

    @Test
    @DisplayName("Корректный текст вопроса без вариантов ответа")
    void shouldHaveCorrectQuestionText(){
        QuestionService questionService = new QuestionService();
        Question question = new Question();
        SetValuedMap<String, String> answers = new HashSetValuedHashMap<>();
        answers.put("ANSWER_1", "");
        answers.put("ANSWER_2", "");
        answers.put("ANSWER_3", "");
        answers.put("ANSWER_4", "");
        question.setText("Test question");
        question.setAnswers(answers);
        question.setRightAnswer("Test2");
        Assertions.assertEquals("Question text: Test question" + System.lineSeparator() +
                "A question with no possible answers, the answer needs to be entered yourself." + System.lineSeparator(), questionService.printQuestion(question));
    }
}
