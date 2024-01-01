package ru.job4j.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CyclicIterator<T> implements Iterator<T> {
    private List<T> data;
    private int index;
    private boolean f;

    public CyclicIterator(List<T> data) {
        if (data != null && !data.isEmpty()) {
            this.data = data;
            f = true;
        }
    }

    @Override
    public boolean hasNext() {
        if (f) {
            if (index == data.size()) {
                index = 0;
            }
        }
        return f;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data.get(index++);
    }
}
