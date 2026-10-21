package org.example;

import java.util.Objects;

public class Student implements Comparable<Student>{
    private final int groupNumber;
    private final double averageScore;
    private final String recordBookNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }
    public double getAverageScore() {
        return averageScore;
    }
    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public int compareTo(Student o) {
        // Базовая сортировка, например, по номеру зачетной книжки
        return this.recordBookNumber.compareTo(o.recordBookNumber);
    }

    public String toFileLine() {
        return groupNumber + ";" + averageScore + ";" + recordBookNumber;
    }

    @Override
    public String toString() {
        return "Студент(" +
                "Группа " + groupNumber +
                ", Средний балл " + averageScore +
                ", Номер зачетной книжки " + recordBookNumber +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return groupNumber == student.groupNumber &&
                Double.compare(student.averageScore, averageScore) == 0 &&
                Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageScore, recordBookNumber);
    }

    public static class Builder {
        private int groupNumber;
        private double averageScore;
        private String recordBookNumber;

        public Builder groupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder averageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public Builder recordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            // Общая проверка для ручного ввода, файла и прямого создания через Builder.
            if (groupNumber <= 0) {
                throw new IllegalArgumentException("Номер группы должен быть больше нуля");
            }
            if (!Double.isFinite(averageScore) || averageScore < 0 || averageScore > 5) {
                throw new IllegalArgumentException("Средний балл должен быть конечным числом от 0 до 5");
            }
            // Проверяем строку без преобразования в число, сохраняя ведущие нули.
            if (recordBookNumber == null || !recordBookNumber.matches("[0-9]*[1-9][0-9]*")) {
                throw new IllegalArgumentException("Номер зачётной книжки должен содержать только цифры и быть больше нуля");
            }
            return new Student(this);
        }
    }
}
