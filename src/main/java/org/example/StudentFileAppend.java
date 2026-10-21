package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Класс для записи в файл (методом append)
 */
public final class StudentFileAppend {
    /**
     * Запись ВСЕЙ отсортированной коллекции студентов в файл (в режиме дозаписи)
     */
    public static void append(Path path, CustomArrayList<Student> students) throws IOException {
        // Третий параметр true обеспечивает дозапись данных в конец файла, не затирая старые
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8, true))) {
            writer.write("--- Отсортированная коллекция студентов ---");
            writer.newLine();

            for (Student student : students) {
                writer.write(student.toFileLine());
                writer.newLine();
            }

            writer.write("--------------------------------------------");
            writer.newLine();
        }
    }

    /**
     * Запись ОДНОГО найденного студента (например, после поиска)
     */
    public static void appendFoundElement(Path path, Student foundStudent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8, true))) {
            writer.write("--- Найденный элемент ---");
            writer.newLine();

            if (foundStudent != null) {
                writer.write(foundStudent.toFileLine());
            } else {
                writer.write("Элемент не найден.");
            }

            writer.newLine();
            writer.write("-------------------------");
            writer.newLine();
        }
    }
}