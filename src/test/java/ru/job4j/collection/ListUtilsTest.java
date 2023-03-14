package ru.job4j.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertThat(input).hasSize(10).containsSequence(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> expected = Arrays.asList(1, 3, 5, 7, 9);
        ListUtils.removeIf(input, a -> a % 2 == 0);
        assertThat(input).containsExactlyElementsOf(expected);
    }

    @Test
    void whenReplaceIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertThat(input).hasSize(10).containsSequence(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> expected = Arrays.asList(0, -1, 2, -1, 4, -1, 6, -1, 8, -1);
        ListUtils.replaceIf(input, a -> a % 2 != 0, -1);
        assertThat(input).containsExactlyElementsOf(expected);
    }

    @Test
    void whenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, -1, 2, -3, 4, -5, 6, -7, 8, -9));
        assertThat(input).hasSize(10).containsSequence(0, -1, 2, -3, 4, -5, 6, -7, 8, -9);
        List<Integer> expected = Arrays.asList(-1, -3, -5, -7, -9);
        ListUtils.removeAll(input, Arrays.asList(0, 2, 4, 6, 8));
        assertThat(input).containsExactlyElementsOf(expected);
    }
}