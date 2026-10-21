package org.example;

import org.example.sorting.BubbleSort;
import org.example.sorting.QuickSort;
import org.example.sorting.SortStrategy;
import org.example.sorting.EvenFieldSort;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;



public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static CustomArrayList<Student> students = new CustomArrayList<>();

    public static void main(String[] args) {



        boolean running = true;

        // После выполнения команды возвращаемся в меню; пункт 0 завершает цикл.
        while(running) {

            showMenu();

            System.out.println("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    loadStudents();
                    break;

                case "2":
                    showStudents();
                    break;

                case "3":
                    sortStudents(false);
                    break;

                case "4":
                    sortStudents(true);
                    break;

                case "5":
                    saveStudents();
                    break;

                case "6":
                    countOccurrences();
                    break;

                case "0":
                    running = false;
                    System.out.println("Программа завершена");
                    break;

                default:
                    System.out.println("Неизвестная команда");

            }

        }

        scanner.close();

    }

    private static void loadStudents() {

        System.out.println("\n--- ЗАПОЛНЕНИЕ ---");
        System.out.println("1. Вручную");
        System.out.println("2. Случайными данными");
        System.out.println("3. Из файла");
        System.out.print("Выберите способ: ");

        String choice = scanner.nextLine();

        if(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.print("Неверный способ заполнения!");
            return;
        }

        System.out.print("Количество студентов: ");
        int count = readPositiveInt();

        CustomArrayList<Student> newStudents;

        try{
            switch (choice) {

                case "1":
                    newStudents = new CustomArrayList<>();

                    for (int i = 0; i < count; i++) {

                        Student student = StudentStreamLoader.readStudent(scanner, i + 1);

                        newStudents.add(student);
                    }
                    break;

                case "2":
                    newStudents = StudentStreamLoader.random(count, new Random());
                    break;

                case "3":
                    System.out.print("Введите путь к файлу: ");
                    String fileName = scanner.nextLine();

                    newStudents = StudentStreamLoader.fromFile(
                            Path.of(fileName), count);
                    break;

                default:
                    return;
            }

            // Прежний список заменяем только после успешной загрузки всех записей.
            students = newStudents;
            System.out.println("Успешно загружено студентов: " + students.size());

        } catch (IOException | RuntimeException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
    }

private static void showStudents() {

    if (students.size() == 0) {
        System.out.println("Список студентов пуст!");
        return;
    }

    System.out.println("\n--- СПИСОК СТУДЕНТОВ ---");

    // Индексы начинаются с 0, но пользователю
    // показываем нумерацию с 1.
    for (int i = 0; i < students.size(); i++) {
        System.out.println(
                (i + 1) + ". " + students.get(i));
    }
}

// Выбираем реализацию алгоритма через общий интерфейс Strategy.
private static SortStrategy<Student> chooseStrategy() {

    System.out.println("\nВыберите алгоритм:");
    System.out.println("1. Bubble Sort");
    System.out.println("2. Quick Sort");
    System.out.print("Ваш выбор: ");

    String choice = scanner.nextLine();

    switch (choice) {
        case "1":
            return new BubbleSort<>();

        case "2":
            return new QuickSort<>();

        default:
            System.out.println("Неверный алгоритм!");
            return null;
    }
}

// Компаратор задаёт поле сравнения независимо от выбранного алгоритма.
private static Comparator<Student> chooseComparator() {

    System.out.println("\nВыберите поле:");
    System.out.println("1. Номер группы");
    System.out.println("2. Средний балл");
    System.out.println("3. Номер зачётной книжки");
    System.out.print("Ваш выбор: ");

    String choice = scanner.nextLine();

    switch (choice) {
        case "1":
            return new StudentComparator
                    .GroupNumberComparator();

        case "2":
            return new StudentComparator
                    .AverageScoreComparator();

        case "3":
            return new StudentComparator
                    .RecordBookComparator();

        default:
            System.out.println("Неверное поле!");
            return null;
    }
}


private static void sortStudents(boolean evenOnly) {

    if (students.size() == 0) {
        System.out.println("Список студентов пуст!");
        return;
    }

    // Получаем выбранный алгоритм.
    SortStrategy<Student> strategy = chooseStrategy();

    if (strategy == null) {
        return;
    }

    if (evenOnly) {

        // В дополнительном задании сортируем
        // по числовому полю "номер группы".
        //
        // Нечётные номера остаются на своих местах.
        EvenFieldSort<Student> evenSort =
                new EvenFieldSort<>(
                        strategy,
                        Student::getGroupNumber);

        evenSort.sort(students);

    } else {

        // Выбираем одно из трёх полей.
        Comparator<Student> comparator = chooseComparator();

        if (comparator == null) {
            return;
        }

        // Запускаем алгоритм через интерфейс Strategy.
        strategy.sort(students, comparator);
    }

    System.out.println("Сортировка завершена!");

    // Сразу показываем результат.
    showStudents();
}

private static void saveStudents() {

    if (students.size() == 0) {
        System.out.println("Список студентов пуст!");
        return;
    }

    System.out.print("Введите путь к файлу: ");
    String fileName = scanner.nextLine();

    try {
        // append добавляет записи в конец файла.
        // Старое содержимое не удаляется.
        StudentFileAppend.append(
                Path.of(fileName), students);

        System.out.println("Данные сохранены!");

    } catch (IOException | RuntimeException e) {
        System.out.println(
                "Ошибка сохранения: " + e.getMessage());
    }
}

private static void countOccurrences() {

    if (students.size() == 0) {
        System.out.println("Список студентов пуст!");
        return;
    }

    // Сначала показываем список.
    showStudents();

    System.out.print("\nВведите номер студента: ");
    int index = readPositiveInt() - 1;

    // Проверяем границы коллекции.
    if (index >= students.size()) {
        System.out.println("Такого студента нет!");
        return;
    }

    System.out.print("Количество потоков: ");
    int threadCount = readPositiveInt();

    // Получаем студента, которого будем искать.
    Student target = students.get(index);

    // Используем существующий многопоточный метод.
    StudentThreadCounter.countOccurrences(
            students, target, threadCount);
}


    private static void showMenu() {
        System.out.println("\n========== МЕНЮ ==========");
        System.out.println("1. Заполнить список студентов");
        System.out.println("2. Показать студентов");
        System.out.println("3. Сортировать студентов");
        System.out.println("4. Сортировать чётные элементы");
        System.out.println("5. Сохранить список в файл");
        System.out.println("6. Подсчитать вхождения студента");
        System.out.println("0. Выход");
        System.out.println("==========================");
    }


    //Считывает целое число. При ошибке ввода повторяет запрос.
    private static int readPositiveInt() {

        while (true) {
            String input = scanner.nextLine();

            try{
                int number = Integer.parseInt(input.trim());

                if (number > 0) {
                    return number;
                }

                System.out.println("Число должно быть больше нуля. Повторите: ");

            } catch (NumberFormatException e) {
                System.out.println("Введите целое число. Повторите: ");
            }
        }

    }

}
