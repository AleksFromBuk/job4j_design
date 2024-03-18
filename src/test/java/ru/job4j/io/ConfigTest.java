package ru.job4j.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Aleksey");
    }

    @Test
    void whenMoreEq() {
        String path = "./data/more_eq_symbol.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key")).isEqualTo("value=1");
        assertThat(config.value("key2")).isEqualTo("value=");
    }

    @Test
    void whenExceptions() {
        String path = "./data/exc1.properties";
        Config config = new Config(path);
       assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty();

        path = "./data/exc1.properties";
        Config config2 = new Config(path);
        assertThatThrownBy(config2::load)
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty();

        path = "./data/exc3.properties";
        Config config3 = new Config(path);
        assertThatThrownBy(config3::load)
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty();
    }

    @Test
    void whenPairWithComment() {
        String path = "./data/pair_with_comment.properties";
        Config config = new Config(path);
        assertThatThrownBy(() -> config.value("#hibernate.dialect"))
                .isInstanceOf(UnsupportedOperationException.class)
                .message()
                .isNotEmpty();
    }
}