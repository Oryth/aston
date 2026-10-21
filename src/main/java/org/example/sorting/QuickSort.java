
package org.example.sorting;

import java.util.Comparator;
import org.example.CustomArrayList;

/**
 * Класс быстрой сортировки (QuickSort).
 * Реализует интерфейс SortStrategy.
 *
 * Принцип работы:
 * 1. Выбираем опорный элемент (pivot).
 * 2. Переставляем элементы так, чтобы:
 *    - слева находились элементы меньше или равные pivot;
 *    - справа находились элементы больше pivot.
 * 3. Повторяем сортировку отдельно для левой и правой частей.
 */
public class QuickSort<T> implements SortStrategy<T> {

    @Override
    public void sort(CustomArrayList<T> elements, Comparator<T> comparator) {

        // Получаем количество элементов
        int n = elements.size();

        // Если элементов 0 или 1,
        // сортировать ничего не нужно
        if (n < 2) {
            return;
        }

        // Запускаем быструю сортировку
        // 0 - индекс первого элемента
        // n - 1 - индекс последнего элемента
        quickSort(elements, comparator, 0, n - 1);
    }

    /**
     * Метод рекурсивной сортировки.
     *
     * left - левая граница участка списка
     * right - правая граница участка списка
     */
    private void quickSort(
            CustomArrayList<T> elements,
            Comparator<T> comparator,
            int left,
            int right) {

        // Если левая граница меньше правой,
        // значит, в участке больше одного элемента
        if (left < right) {

            // Разделяем список относительно pivot
            // и получаем его окончательную позицию
            int pivotIndex = partition(
                    elements, comparator, left, right
            );

            // Сортируем левую часть списка
            // Опорный элемент уже стоит на своём месте,
            // поэтому его не включаем
            quickSort(
                    elements, comparator,
                    left, pivotIndex - 1
            );

            // Сортируем правую часть списка
            quickSort(
                    elements, comparator,
                    pivotIndex + 1, right
            );
        }
    }

    /**
     * Метод разделения списка относительно pivot.
     *
     * Возвращает индекс, на котором
     * окажется опорный элемент.
     */
    private int partition(
            CustomArrayList<T> elements,
            Comparator<T> comparator,
            int left,
            int right) {

        // Выбираем последний элемент как опорный
        T pivot = elements.get(right);

        // i - индекс границы элементов,
        // которые меньше или равны pivot
        int i = left - 1;

        // Проходим по элементам от left до right - 1
        // Сам pivot пока не трогаем
        for (int j = left; j < right; j++) {

            // Получаем текущий элемент
            T current = elements.get(j);

            // comparator.compare(current, pivot):
            //
            // Отрицательное число:
            // current должен идти раньше pivot.
            //
            // Ноль:
            // элементы равны по выбранному полю.
            //
            // Положительное число:
            // current должен идти после pivot.

            // Если current меньше или равен pivot
            if (comparator.compare(current, pivot) <= 0) {

                // Увеличиваем границу меньших элементов
                i++;

                // Меняем элементы местами
                T temp = elements.get(i);
                elements.set(i, elements.get(j));
                elements.set(j, temp);
            }
        }

        // После цикла ставим pivot между
        // меньшими и большими элементами
        T temp = elements.get(i + 1);
        elements.set(i + 1, elements.get(right));
        elements.set(right, temp);

        // Возвращаем индекс pivot
        return i + 1;
    }
}
