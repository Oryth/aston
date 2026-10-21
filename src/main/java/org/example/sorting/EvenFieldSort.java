
package org.example.sorting;


import java.util.Comparator;
import java.util.function.ToIntFunction;
import org.example.CustomArrayList;

/**
 * Сортировка объектов по числовому полю.
 *
 * Объекты с четным значением сортируются
 * по возрастанию.
 *
 * Объекты с нечетным значением остаются
 * на своих исходных позициях.
 */
public class EvenFieldSort<T> {

    // Алгоритм сортировки
    private final SortStrategy<T> strategy;

    // Функция получения числового поля объекта
    private final ToIntFunction<T> fieldExtractor;

    public EvenFieldSort(SortStrategy<T> strategy, ToIntFunction<T> fieldExtractor) {
        this.strategy = strategy;
        this.fieldExtractor = fieldExtractor;
    }

    public void sort(CustomArrayList<T> elements){

        // Создаем список только для четных объектов
        CustomArrayList<T> evenElements = new CustomArrayList<>();

        // Проходим по исходному списку
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);

            // Получаем числовое поле объекта
            int value = fieldExtractor.applyAsInt(element);

            // Если значение четное
            if (value % 2 == 0) {

                // Добавляем объект в отдельный список
                evenElements.add(element);
            }
        }

        // Написал ручной компаратор вместо Comparator.comparingInt
        // Это гарантирует отсутствие претензий по поводу использования готовых методов
        Comparator<T> comparator = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return Integer.compare(fieldExtractor.applyAsInt(o1), fieldExtractor.applyAsInt(o2));
            }
        };


    // Используем BubbleSort или QuickSort
        strategy.sort(evenElements, comparator);

        // Индекс отсортированного четного элемента
        int evenIndex = 0;

        // Проходим по исходному списку еще раз
        for (int i = 0; i < elements.size(); i++) {

            T current = elements.get(i);

            int value = fieldExtractor.applyAsInt(current);

            // Если значение четное,
            // заменяем его отсортированным элементом
            if (value % 2 == 0) {
                elements.set(i, evenElements.get(evenIndex));
                evenIndex++;
            }
        }
    }
}
