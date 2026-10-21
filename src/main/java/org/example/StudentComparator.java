package org.example;

import java.util.Comparator;

public class StudentComparator {

    public static class GroupNumberComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return Integer.compare(s1.getGroupNumber(), s2.getGroupNumber());
        }
    }

    public static class AverageScoreComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return Double.compare(s2.getAverageScore(), s1.getAverageScore());
        }
    }

    public static class RecordBookComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getRecordBookNumber().compareTo(s2.getRecordBookNumber());
        }
    }
}

