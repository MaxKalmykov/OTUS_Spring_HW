package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import ru.otus.hw.entities.Student;

import java.util.Scanner;

@ToString
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final Scanner scanner;
    private final MessagePrinter messagePrinter;

    private String setStudentData(String dataName){
        StringBuilder s = new StringBuilder();
        System.out.println(messagePrinter.getMessage("student.enter") + " " + dataName + ":");
        s.append(scanner.nextLine());
        while (s.toString().trim().length() <= 0) {
            System.out.println(messagePrinter.getMessage("student.incorrect") + " " + dataName + "! " + messagePrinter.getMessage("student.try-again"));
            s.delete(0, s.length());
            s.append(scanner.nextLine());
        }
        return s.toString();
    }

    @Override
    public Student initStudent(){
        String lastName;
        String firstName;
        lastName = setStudentData(messagePrinter.getMessage("student.last-name"));
        firstName = setStudentData(messagePrinter.getMessage("student.first-name"));

        return new Student(lastName, firstName);
    }
}