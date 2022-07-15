package ru.otus.hw.entities;

import com.opencsv.bean.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.SetValuedMap;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Question {
    @CsvBindByName(column = "QUESTION_TEXT")
    private String text;
    @CsvBindAndJoinByName(column = "ANSWER_[1-9]", elementType = String.class, mapType = SetValuedMap.class)
    private SetValuedMap<String, String> answers;
    @CsvBindByName(column = "RIGHT_ANSWER")
    private String rightAnswer;

    public List<String> getAnswerList() {
        List<String> res = new ArrayList<>();
        for (String e : this.getAnswers().values()) {
            if (e.trim().length() > 0) {
                res.add(e);
            }
        }
        return res;
    }

    public boolean isQuestionTypeWithAnswerOption() {
        return getAnswerList().size() > 0;
    }

}