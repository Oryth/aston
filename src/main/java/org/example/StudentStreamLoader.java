package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Класс для заполнения списка студентов: fromFile - из файла, random - рандомно
 */
public final class StudentStreamLoader {

    public static CustomArrayList<Student> fromFile(Path path, int count) throws IOException {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {

            CustomArrayList<Student> result = lines
                    .filter(line -> !line.isBlank())
                    .limit(count)
                    .map(StudentSources::parse)
                    .collect(
                            CustomArrayList<Student>::new,
                            CustomArrayList<Student>::add,
                            CustomArrayList<Student>::addAll
                    );

            if (result.size() != count) {
                throw new RuntimeException("Не хватает записей: ожидается " + count + ", нашлось " + result.size());
            }

            return result;
        }
    }

    public static CustomArrayList<Student> random(int count, Random random) {
        return IntStream.range(0, count)
                .mapToObj(index -> new Student.Builder()
                        .groupNumber(random.nextInt(100) + 1)
                        .averageGrade(random.nextInt(501) / 100.0)
                        .recordBookNumber(
                                100_000L + random.nextInt(900_000)
                        )
                        .build())
                .collect(
                        CustomArrayList<Student>::new,
                        CustomArrayList<Student>::add,
                        CustomArrayList<Student>::addAll
                );
    }

    public static Student parse(String line) {
        String[] fields = line.split(";", -1);

        if (fields.length != 3) {
            throw new RuntimeException("Неправильный формат. Правильный: №;№;№");
        }

        try {
            return new Student.Builder()
                    .groupNumber(Integer.parseInt(fields[0].trim()))
                    .averageGrade(Double.parseDouble(fields[1].trim()))
                    .recordBookNumber(Long.parseLong(fields[2].trim()))
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Неправильная строка: " + line + ". " + e.getMessage(), e);
        }
    }

    public static Student readStudent(Scanner scanner, int number) {
        while (true) {
            System.out.println("Студент " + number + ": введите №;№;№");
            String line = scanner.nextLine();
            try {
                return parse(line);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}