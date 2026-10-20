package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student.Builder().groupNumber(102).averageScore(4.2).recordBookNumber("B-992").build();
        Student s2 = new Student.Builder().groupNumber(101).averageScore(4.9).recordBookNumber("A-554").build();
        Student s3 = new Student.Builder().groupNumber(105).averageScore(3.8).recordBookNumber("C-112").build();

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        System.out.println("=== Исходный список ===");
        for (Student s : students) {
            System.out.println(s);
        }

        System.out.println("\n=== Сортировка по номеру ГРУППЫ ===");
        students.sort(new StudentComparator.GroupNumberComparator());
        for (Student s : students) {
            System.out.println(s);
        }

        System.out.println("\n=== Сортировка по СРЕДНЕМУ БАЛЛУ ===");
        students.sort(new StudentComparator.AverageScoreComparator());
        for (Student s : students) {
            System.out.println(s);
        }

        System.out.println("\n=== Сортировка по № ЗАЧЁТКИ ===");
        students.sort(new StudentComparator.RecordBookComparator());
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
