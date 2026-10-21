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

    public static void append(Path path, CustomArrayList<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8, true))) {
            for (Student student : students) {
                writer.write(student.toFileLine());
                writer.newLine();
            }
        }
    }
}