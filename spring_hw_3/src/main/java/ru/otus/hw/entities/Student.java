package ru.otus.hw.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@ToString
public class Student {

    private final String lastName;
    private final String firstName;

    public Student(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}