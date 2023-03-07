package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {
    private int size = 0;
    private int modCount = 0;
    private Node<E> head;

    @Override
    public void add(E value) {
        Node<E> service = serviceNode();
        if (size == 0) {
            head = new Node<>(value, null);
        } else {
            for (int i = 1; i < size; i++) {
                service = service.next;
            }
            service.next = new Node<E>(value, null);
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> service = serviceNode();
        for (int i = 0; i < index; i++) {
            service = service.next;
        }
        modCount++;
        return service.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> node = serviceNode();
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return node != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E res = node.item;
                node = node.next;
                return res;
            }
        };
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E value, Node<E> next) {
            this.item = value;
            this.next = next;
        }
    }

    private Node<E> serviceNode() {
        return head;
    }
}