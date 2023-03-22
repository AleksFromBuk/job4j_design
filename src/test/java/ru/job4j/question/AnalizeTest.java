package ru.job4j.question;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class AnalizeTest {
    @Test
    void whenNotChanged() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3);
        Set<User> previous = new HashSet<>(toInitialize);
        Set<User> current = new HashSet<>(toInitialize);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(0, 0, 0));
    }

    @Test
    void whenOneChanged() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3);
        Set<User> previous = new HashSet<>(toInitialize);
        List<User> after = Arrays.asList(u1, new User(2, "BB"), u3);
        Set<User> current = new HashSet<>(after);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(0, 1, 0));
    }

    @Test
    void whenOneDeleted() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3);
        Set<User> previous = new HashSet<>(toInitialize);
        List<User> after = Arrays.asList(u1, u3);
        Set<User> current = new HashSet<>(after);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(0, 0, 1));
    }

    @Test
    void whenOneAdded() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3);
        Set<User> previous = new HashSet<>(toInitialize);
        List<User> after = Arrays.asList(u1, u2, u3, new User(4, "D"));
        Set<User> current = new HashSet<>(after);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(1, 0, 0));
    }

    @Test
    void whenAllChanged() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3);
        Set<User> previous = new HashSet<>(toInitialize);
        List<User> after = Arrays.asList(new User(1, "AA"), u2, new User(4, "D"));
        Set<User> current = new HashSet<>(after);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(1, 1, 1));
    }

    @Test
    void whenAllChangedInPrevious() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3);
        Set<User> previous = new HashSet<>(toInitialize);
        List<User> after = Arrays.asList(new User(1, "AA"), new User(2, "BB"), new User(3, "D"));
        Set<User> current = new HashSet<>(after);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(0, 3, 0));
    }

    @Test
    void whenAllOptionsOnPrevious() {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        User u4 = new User(4, "C");
        List<User> toInitialize = Arrays.asList(u1, u2, u3, u4);
        Set<User> previous = new HashSet<>(toInitialize);
        List<User> after = Arrays.asList(new User(1, "AA"), new User(2, "BB"), new User(5, "D"), new User(6, "BB"));
        Set<User> current = new HashSet<>(after);
        assertThat(Analize.diff(previous, current)).isEqualTo(new Info(2, 2, 2));
    }

}