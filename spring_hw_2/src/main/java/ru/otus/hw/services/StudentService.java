package ru.otus.hw.services;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.otus.hw.entities.Student;

import java.util.Scanner;

@Getter
@Setter
@ToString
public class StudentService {

    private String setStudentData(String dataName){
        Scanner scanner = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        System.out.println("Enter your " + dataName + ":");
        s.append(scanner.nextLine());
        while (s.toString().trim().length() <= 0) {
            System.out.println("Incorrect " + dataName + "! Try again.");
            s.delete(0, s.length());
            s.append(scanner.nextLine());
        }
        return s.toString();
    }
    public Student initStudent(){
        String lastName;
        String firstName;
        lastName = setStudentData("last name");
        firstName = setStudentData("first name");

        return new Student(lastName, firstName);
    }
}
