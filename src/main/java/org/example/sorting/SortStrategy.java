package org.example.sorting;

import java.util.Comparator;
import org.example.CustomArrayList;


public interface SortStrategy<T> {

    /**
     * Метод сортировки
     * @param elements коллекция для сортировки.
     * @param comparator объект, который знает, как сравнивать два элемента
     */
    void sort(CustomArrayList<T> elements, Comparator<T> comparator);
}