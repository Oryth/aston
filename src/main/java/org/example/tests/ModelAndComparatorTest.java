package org.example.tests;

import org.example.Student;
import org.example.StudentComparator;

public class ModelAndComparatorTest {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ТЕСТОВ: BUILDER И КОМПАРАТОРЫ ===");

        testBuilder();
        testGroupComparator();
        testAverageScoreComparator();
        testRecordBookComparator();

        System.out.println("============================================\n");
    }

    private static void testBuilder() {
        Student student = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordBookNumber("12345")
                .build();

        if (student.getGroupNumber() == 101 &&
                student.getAverageScore() == 4.5 &&
                "12345".equals(student.getRecordBookNumber())) {
            System.out.println("Тест Builder: УСПЕШНО (Данные установлены корректно)");
        } else {
            System.out.println("Тест Builder: ОШИБКА (Данные не совпадают)");
        }
    }

    private static void testGroupComparator() {
        Student s1 = new Student.Builder().groupNumber(5).build();
        Student s2 = new Student.Builder().groupNumber(10).build();

        StudentComparator.GroupNumberComparator comp = new StudentComparator.GroupNumberComparator();

        if (comp.compare(s1, s2) < 0 && comp.compare(s2, s1) > 0 && comp.compare(s1, s1) == 0) {
            System.out.println("Тест GroupNumberComparator: УСПЕШНО");
        } else {
            System.out.println("Тест GroupNumberComparator: ОШИБКА");
        }
    }

    private static void testAverageScoreComparator() {
        Student s1 = new Student.Builder().averageScore(3.8).build();
        Student s2 = new Student.Builder().averageScore(4.9).build();

        StudentComparator.AverageScoreComparator comp = new StudentComparator.AverageScoreComparator();

        if (comp.compare(s1, s2) > 0 && comp.compare(s2, s1) < 0) {
            System.out.println("Тест AverageScoreComparator: УСПЕШНО");
        } else {
            System.out.println("Тест AverageScoreComparator: ОШИБКА");
        }
    }

    private static void testRecordBookComparator() {
        Student s1 = new Student.Builder().recordBookNumber("A").build();
        Student s2 = new Student.Builder().recordBookNumber("B").build();

        StudentComparator.RecordBookComparator comp = new StudentComparator.RecordBookComparator();

        if (comp.compare(s1, s2) < 0 && comp.compare(s2, s1) > 0) {
            System.out.println("Тест RecordBookComparator: УСПЕШНО");
        } else {
            System.out.println("Тест RecordBookComparator: ОШИБКА");
        }
    }
}
