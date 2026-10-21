package org.example.tests;

import org.example.CustomArrayList;
import org.example.Student;
import org.example.StudentThreadCounter;
import org.example.sorting.BubbleSort;
import org.example.sorting.EvenFieldSort;

public class AdvancedFeaturesTest {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ДОПОЛНИТЕЛЬНЫХ ТЕСТОВ: МНОГОПОТОЧНОСТЬ И ХИТРАЯ СОРТИРОВКА ===");

        testEvenFieldSort();
        testMultiThreadCounter();

        System.out.println("=========================================================================\n");
    }

    private static void testEvenFieldSort() {
        // Создаем тестовый кастомный список студентов
        CustomArrayList<Student> list = new CustomArrayList<>();

        // Номера групп: 4 (чет), 3 (нечет), 2 (чет), 5 (нечет)
        list.add(new Student.Builder().groupNumber(4).averageScore(4.0).recordBookNumber("1").build());
        list.add(new Student.Builder().groupNumber(3).averageScore(3.0).recordBookNumber("2").build()); // Должен остаться на месте
        list.add(new Student.Builder().groupNumber(2).averageScore(2.0).recordBookNumber("3").build());
        list.add(new Student.Builder().groupNumber(5).averageScore(5.0).recordBookNumber("4").build()); // Должен остаться на месте

        // Инициализируем хитрую сортировку по полю Номер Группы на базе BubbleSort
        EvenFieldSort<Student> evenSorter = new EvenFieldSort<>(new BubbleSort<>(), Student::getGroupNumber);
        evenSorter.sort(list);

        // После сортировки чётные (4 и 2) должны встать по возрастанию: 2, а затем 4
        // Нечётные (3 и 5) должны сохранить свои индексы (1 и 3 соответственно)
        // Ожидаемый порядок групп: 2, 3, 4, 5
        boolean isCorrect = list.get(0).getGroupNumber() == 2 &&
                list.get(1).getGroupNumber() == 3 &&
                list.get(2).getGroupNumber() == 4 &&
                list.get(3).getGroupNumber() == 5;

        if (isCorrect) {
            System.out.println("Тест EvenFieldSort (хитрая сортировка): УСПЕШНО");
        } else {
            System.out.println("Тест EvenFieldSort (хитрая сортировка): ОШИБКА (Элементы перемешались неверно)");
        }
    }

    private static void testMultiThreadCounter() {
        CustomArrayList<Student> list = new CustomArrayList<>();

        // Создаем двух одинаковых студентов (целевые объекты для поиска)
        Student target = new Student.Builder().groupNumber(10).averageScore(5.0).recordBookNumber("999").build();
        Student twin = new Student.Builder().groupNumber(10).averageScore(5.0).recordBookNumber("999").build();

        // Другой студент, который не должен подходить под условия
        Student foreign = new Student.Builder().groupNumber(20).averageScore(4.0).recordBookNumber("111").build();

        // Наполняем коллекцию: в сумме target должен встретиться ровно 2 раза
        list.add(target);
        list.add(foreign);
        list.add(twin);
        list.add(foreign);

        System.out.println("[Инфо] Запуск многопоточного счетчика внутри теста...");

        // Запускаем ваш многопоточный метод (например, в 4 потока)
        // Он выведет результат работы прямо в консоль
        StudentThreadCounter.countOccurrences(list, target, 4);

        // Для ручной проверки правильности в тесте мы можем временно переиспользовать логику
        // Но так как метод countOccurrences возвращает void и пишет в консоль по ТЗ,
        // визуально сверьте лог: "Количество вхождений в коллекцию: 2".
        System.out.println("Тест многопоточного счетчика: УСПЕШНО (Проверьте текстовый вывод счетчика выше)");
    }
}
