package ru.job4j.set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleSetTest {

    @Test
    void whenAddNonNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
    }

    @Test
    void whenAddNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(null)).isTrue();
        assertThat(set.add(null)).isFalse();
    }

    @Test
    void whenContainsAndAdd() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(-1)).isTrue();
        assertThat(set.add(0)).isTrue();
        assertThat(set.add(1)).isTrue();
        assertThat(set.add(-1)).isFalse();
        assertThat(set.add(0)).isFalse();
        assertThat(set.add(1)).isFalse();
    }
}