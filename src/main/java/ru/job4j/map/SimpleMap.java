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
        int oldCap = capacity;
        capacity = capacity << 1;
        MapEntry<K, V>[] newTab = new MapEntry[capacity];
        for (int j = 0; j < oldCap; j++) {
            if (table[j] == null) {
                continue;
            }
            MapEntry<K, V> add = new MapEntry<>(table[j].key, table[j].value);
            newTab[indexFor(Objects.hashCode(add.key))] = add;
            table[j].key = null;
            table[j].value = null;
            table[j] = null;
        }
        table = newTab;
    }

    @Override
    public V get(K key) {
        V res = null;
        int index = getIndex(key);
        if (table[index] != null) {
            if (checkKeysEquals(table[index], key)) {
                res = table[index].value;
            }
        }
        return res;
    }

    @Override
    public boolean remove(K key) {
        int index = getIndex(key);
        boolean res = false;
        if (table[index] != null) {
            if (checkKeysEquals(table[index], key))  {
                table[index].key = null;
            }
            table[index].value = null;
            table[index] = null;
            count--;
            modCount++;
            res = true;
        }
        return res;
    }

    private int getIndex(K key) {
        return indexFor(hash(Objects.hashCode(key)));
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {

            int index = 0;
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                for (; index < capacity; index++) {
                    if (table[index] != null) {
                        break;
                    }
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private boolean checkKeysEquals(MapEntry tableCurrentValue, K key) {
        return (Objects.hashCode(tableCurrentValue.key) == Objects.hashCode(key)
                && Objects.equals(key, tableCurrentValue.key));
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