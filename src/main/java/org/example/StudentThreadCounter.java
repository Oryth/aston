package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class StudentThreadCounter {
    /**
            * Многопоточный метод подсчета вхождений элемента N
     * @param list кастомная коллекция студентов
     * @param targetStudent элемент N, количество вхождений которого нужно найти
     * @param threadCount количество потоков, на которое разбивается задача
     */
    public static void countOccurrences(CustomArrayList<Student> list, Student targetStudent, int threadCount) {
        if (list == null || list.size() == 0) {
            System.out.println("Количество вхождений элемента: 0 (коллекция пуста)");
            return;
        }

        int totalSize = list.size();
        // Корректируем количество потоков, если элементов меньше, чем запрошено потоков
        int actualThreads = Math.min(threadCount, totalSize);

        // Потокобезопасный счетчик для общего результата
        AtomicInteger totalCount = new AtomicInteger(0);

        // Массив для хранения потоков
        Thread[] threads = new Thread[actualThreads];

        // Вычисляем размер порции данных для каждого потока
        int chunkSize = totalSize / actualThreads;

        for (int i = 0; i < actualThreads; i++) {
            final int threadIndex = i;
            // Определяем границы участка коллекции для текущего потока
            final int start = threadIndex * chunkSize;
            // Последний поток забирает остаток, если размер не делится нацело
            final int end = (threadIndex == actualThreads - 1) ? totalSize : start + chunkSize;

            // Создаем поток с помощью лямбда-выражения (реализация интерфейса Runnable)
            threads[i] = new Thread(() -> {
                int localCount = 0;
                for (int j = start; j < end; j++) {
                    Student current = list.get(j);
                    // Сравниваем объекты через переопределенный метод equals
                    if (current != null && current.equals(targetStudent)) {
                        localCount++;
                    }
                }
                // Атомарно добавляем локальный результат потока к общему счетчику
                totalCount.addAndGet(localCount);
            });

            // Запускаем поток
            threads[i].start();
        }

        // Ожидаем завершения всех потоков (join), чтобы гарантировать точность результата
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Многопоточный подсчет был прерван: " + e.getMessage());
            Thread.currentThread().interrupt();
            return;
        }

        // Выводим итоговый результат в консоль, как требует ТЗ
        System.out.println("=== Результат многопоточного подсчета ===");
        System.out.println("Элемент N: " + targetStudent);
        System.out.println("Количество вхождений в коллекцию: " + totalCount.get());
        System.out.println("Подсчет произведен с использованием " + actualThreads + " поток(ов).");
    }
}
