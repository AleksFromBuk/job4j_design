package ru.job4j.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

public class SimpleSet<T> implements Set<T> {
    private SimpleArrayList<T> set = new SimpleArrayList<>(0);

    @Override
    public boolean add(T value) {
        boolean f = !contains(value);
        if (f) {
            set.add(value);
        }
        return f;
    }

    @Override
    public boolean contains(T element) {
        boolean f = false;
        for (T it : set) {
            if (Objects.equals(it, element)) {
                f = true;
                break;
            }
        }
        return f;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}