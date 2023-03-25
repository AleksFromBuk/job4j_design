package ru.job4j.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean rsl = false;
        if (!findBy(child).isPresent()) {
            Optional<Node<E>> tmp = findBy(parent);
            if (tmp.isPresent()) {
                rsl = tmp.get().children.add(new Node<>(child));
            }
        }
        return rsl;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.value.equals(value)) {
                rsl = Optional.of(el);
                break;
            }
            data.addAll(el.children);
        }
        return rsl;
    }

    public static void main(String[] args) {
        SimpleTree<Integer> tree = new SimpleTree<>(7);
        int cnt = 1;
        for (int i = 0; i < 4; i++) {
            tree.root.children.add(new Node<>(cnt++));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tree.root.children.get(i).children.add(new Node<>(cnt++));
            }
        }
        tree.findBy(9);
    }
}