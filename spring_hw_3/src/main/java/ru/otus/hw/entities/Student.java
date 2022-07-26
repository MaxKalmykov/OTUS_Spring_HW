package ru.otus.hw.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@RequiredArgsConstructor
public class Student {

    private final String lastName;
    private final String firstName;

}