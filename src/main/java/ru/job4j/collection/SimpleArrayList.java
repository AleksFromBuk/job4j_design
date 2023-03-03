package ru.job4j.collection;

import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {
    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        modCount++;
        if (size == container.length) {
            container = grow();
        }
        container[size++] = value;
    }

    private T[] grow() {
        return Arrays.copyOf(container, container.length * 2 + 10);
    }

    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        modCount++;
        T res = container[index];
        System.arraycopy(
                container, index + 1,
                container,
                index,
                container.length - index - 1
        );
        container[--size] = null;
        return res;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int cursor = 0;
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                checkForComodification();
                return cursor < size;
            }

            @Override
            public T next() {
                checkForComodification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[cursor++];
            }

            final void checkForComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}