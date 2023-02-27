package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {
    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        boolean res = false;
        while (index < data.length) {
            if (Math.abs(data[index]) % 2 == 0) {
                res = true;
                break;
            } else {
                index++;
            }
        }
        return res;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[index++];
    }
}