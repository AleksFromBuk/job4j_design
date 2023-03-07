package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size = 0;
    private int modCount = 0;
    private Node<T> head;

    public void add(T value) {
        Node<T> service = head;
        if (size == 0) {
            head = new Node<>(value, null);
        } else {
            for (int i = 1; i < size; i++) {
                service = service.next;
            }
            service.next = new Node<T>(value, null);
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> service = head;
        for (int i = 0; i < index; i++) {
            service = service.next;
        }
        return service.item;
    }

    public void addFirst(T value) {
        head = new Node<T>(value, head);
        modCount++;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T res = head.item;
        Node<T> tmp = head;
        head = head.next;
        tmp.next = null;
        tmp.item = null;
        size--;
        modCount++;
        return res;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> node = head;
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T res = node.item;
                node = node.next;
                return res;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

}