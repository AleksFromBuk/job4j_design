package ru.job4j.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleTreeTest {

    @Test
    void when6ElFindLastThen6() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.findBy(6)).isPresent();
    }

    @Test
    void whenElFindNotExistThenOptionEmpty() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        assertThat(tree.findBy(7)).isEmpty();
    }

    @Test
    void whenChildExistOnLeafThenNotAdd() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.add(2, 6)).isFalse();
    }

    @Test
    void whenIsBinaryAndOnlyTwoChild() {
        SimpleTree<Integer> tree = new SimpleTree<>(7);
        for (int i = 1; i <= 2; i++) {
            tree.add(7, i);
        }
        int cnt = 3;
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 2; j++) {
                tree.add(i, cnt++);
            }
        }
        assertThat(tree.isBinary()).isTrue();
    }

    @Test
    void whenIsBinaryTwoAndOneChild() {
        SimpleTree<Integer> tree = new SimpleTree<>(7);
        for (int i = 1; i <= 2; i++) {
            tree.add(7, i);
        }
        int cnt = 3;
        for (int i = 1; i <= 2; i++) {
            tree.add(i, cnt++);
        }
        assertThat(tree.isBinary()).isTrue();
    }

    @Test
    void whenIsNotBinaryTree() {
        SimpleTree<Integer> tree = new SimpleTree<>(7);
        for (int i = 1; i <= 3; i++) {
            tree.add(7, i);
        }
        assertThat(tree.isBinary()).isFalse();
    }

}