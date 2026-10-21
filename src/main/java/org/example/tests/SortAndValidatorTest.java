package org.example.tests;

import org.example.CustomArrayList;
import org.example.Student;
import org.example.StudentComparator;
import org.example.StudentStreamLoader;
import org.example.sorting.BubbleSort;
import org.example.sorting.SortStrategy;

public class SortAndValidatorTest {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ТЕСТОВ: СОРТИРОВКА И ВАЛИДАТОР ===");

        testValidatorCorrectLine();
        testValidatorIncorrectLine();
        testSortingAlgorithm();

        System.out.println("============================================\n");
    }

    private static void testValidatorCorrectLine() {
        try {
            // Проверяем валидацию корректной строки через ваш метод parse
            Student student = StudentStreamLoader.parse("102; 4.8; 54321");

            if (student.getGroupNumber() == 102 && student.getAverageScore() == 4.8) {
                System.out.println("Тест Валидатора (корректная строка): УСПЕШНО");
            } else {
                System.out.println("Тест Валидатора (корректная строка): ОШИБКА (Парсинг полей некорректен)");
            }
        } catch (Exception e) {
            System.out.println("Тест Валидатора (корректная строка): ОШИБКА (" + e.getMessage() + ")");
        }
    }

    private static void testValidatorIncorrectLine() {
        try {
            // Передаем сломанную строку, валидатор должен выбросить ошибку
            StudentStreamLoader.parse("не_число; 4.8; 54321");

            // Если мы дошли до этой строки, значит исключение НЕ выбросилось — это баг
            System.out.println("Тест Валидатора (некорректная строка): ОШИБКА (Валидатор пропустил неверный формат!)");
        } catch (RuntimeException e) {
            // Если упало в catch — валидатор сработал верно и защитил программу от плохих данных
            System.out.println("Тест Валидатора (некорректная строка): УСПЕШНО (Схвачена правильная ошибка валидации)");
        }
    }

    private static void testSortingAlgorithm() {
        // Создаем тестовую коллекцию
        CustomArrayList<Student> list = new CustomArrayList<>();
        list.add(new Student.Builder().groupNumber(30).build());
        list.add(new Student.Builder().groupNumber(10).build());
        list.add(new Student.Builder().groupNumber(20).build());

        // Используем одну из ваших ручных стратегий (например, BubbleSort)
        SortStrategy<Student> strategy = new BubbleSort<>();
        strategy.sort(list, new StudentComparator.GroupNumberComparator());

        // Проверяем, выстроились ли элементы по возрастанию (10 -> 20 -> 30)
        boolean isSorted = list.get(0).getGroupNumber() == 10 &&
                list.get(1).getGroupNumber() == 20 &&
                list.get(2).getGroupNumber() == 30;

        if (isSorted) {
            System.out.println("Тест алгоритма сортировки: УСПЕШНО (Элементы упорядочены)");
        } else {
            System.out.println("Тест алгоритма сортировки: ОШИБКА (Порядок элементов нарушен)");
        }
    }
}
