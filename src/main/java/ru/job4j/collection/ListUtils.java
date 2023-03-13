package ru.job4j.collection;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtils {
    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (iterator.nextIndex() == index) {
                iterator.add(value);
                break;
            }
            iterator.next();
        }
    }

    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index + 1, list.size());
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (iterator.nextIndex() == index + 1) {
                iterator.add(value);
                break;
            }
            iterator.next();
        }
    }

    public static <T> void removeIf(List<T> list, Predicate<T> filter) {
        List<T> res = list.stream()
                .filter(filter)
                .collect(Collectors.toList());
        list.retainAll(res);
    }

    public static <T> void replaceIf(List<T> list, Predicate<T> filter, T value) {
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.set(value);
            }
        }
    }

    public static <T> void removeAll(List<T> list, List<T> elements) {
        List<T> res = list.stream()
                .filter(o -> !elements.contains(o))
                .collect(Collectors.toList());
        list.retainAll(res);
    }
}