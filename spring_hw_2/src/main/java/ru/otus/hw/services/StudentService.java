package ru.otus.hw.services;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import ru.otus.hw.entities.Student;

import java.util.Scanner;

@ToString
@Service
public class StudentService {

    private Scanner scanner;
    public StudentService() {
        this.scanner = new Scanner(System.in);
    }

    private String setStudentData(String dataName){
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
