package org.example.sorting;

import java.util.Comparator;
import java.util.List;


public interface SortStrategy<T> {

    /**
     * Метод сортировки
     * @param elements коллекция для сортировки.
     * @param comparator объект, который знает, как сравнивать два элемента
     */
    void sort(List<T> elements, Comparator<T> comparator);

}