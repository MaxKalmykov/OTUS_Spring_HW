package ru.otus.hw.entities;

import com.opencsv.bean.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.SetValuedMap;

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

}
