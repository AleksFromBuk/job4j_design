package ru.job4j.map;

import java.util.*;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean res = false;
        int index = indexFor(hash(Objects.hashCode(key)));
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            if ((float) count / capacity >= LOAD_FACTOR) {
                expand();
            }
            modCount++;
            res = true;
        }
        return res;
    }

    private int hash(int hashCode) {
        return (hashCode ^ (hashCode >>> 16));
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        MapEntry<K, V>[] oldTab = table;
        MapEntry<K, V>[] newTab = new MapEntry[capacity << 1];
        table = newTab;
        int oldCap = capacity;
        capacity = capacity << 1;
        for (int j = 0; j < oldCap; j++) {
            if (oldTab[j] == null) {
                continue;
            }
            MapEntry<K, V> add = new MapEntry<>(oldTab[j].key, oldTab[j].value);
            newTab[indexFor(Objects.hashCode(add.key))] = add;
            oldTab[j].key = null;
            oldTab[j].value = null;
            oldTab[j] = null;
        }
    }

    @Override
    public V get(K key) {
        int index = indexFor(hash(Objects.hashCode(key)));
        MapEntry<K, V> tmp = table[index];
        V res = null;
        if (tmp != null) {
            if (Objects.hashCode(tmp.key) == Objects.hashCode(key)
                    && Objects.equals(key, tmp.key)) {
                res = tmp.value;
            }
        }
        return res;
    }

    @Override
    public boolean remove(K key) {
        int index = indexFor(hash(Objects.hashCode(key)));
        boolean res = false;
        if (table[index] != null) {
            if (table[index].key != null) {
                if (Objects.hashCode(table[index].key) == Objects.hashCode(key)
                        && Objects.equals(key, table[index].key)) {
                    table[index].key = null;
                }
            }
            table[index].value = null;
            table[index] = null;
            count--;
            modCount++;
            res = true;
        }
        return res;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            int cursor = 0;
            final int expectedModCount = modCount;
            int index = 0;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return cursor < count;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                for (; index < table.length && table[index] == null;) {
                    index++;
                }
                cursor++;
                return table[index++].key;
            }

        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}