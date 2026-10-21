package org.example;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class CustomArrayList<T> implements Iterable<T> {

    private static final int DEFAULT_SIZE = 10;
    private Object[] elements = new Object[DEFAULT_SIZE];
    private int size;

    public void add(T element) {
        checkCapacity();
        elements[size++] = element;
    }

    public void addAll(CustomArrayList<? extends T> otherList) {
        int oldSize = otherList.size();
        for (int i = 0; i < oldSize; i++) {
            add(otherList.get(i));
        }
    }

    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    public void set(int index, T element) {
        checkIndex(index);
        elements[index] = element;
    }

    public int size() {
        return size;
    }

    private void checkCapacity() {
        if (size < elements.length) {
            return;
        }
        Object[] newObject = new Object[elements.length * 2];

        for (int i = 0; i < size; i++) {
            newObject[i] = elements[i];
        }

        elements = newObject;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException( "Index: " + index + ", size: " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int cursor;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(cursor++);
            }
        };
    }
}