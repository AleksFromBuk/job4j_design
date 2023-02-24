package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleModelTest {
    /**
     * Так можно проверить генерацию исключения, когда метод класса не принимает аргументы
     */
    @Test
    void checkGetName() {
        SimpleModel sm = new SimpleModel();
        assertThatThrownBy(sm::getName)
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Так проверяется метод, принимающий аргументы/void-type:
     */
    @Test
    void checkName() {
        SimpleModel sm = new SimpleModel();
        assertThatThrownBy(() -> sm.setName("Name", 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Так можно проверить наличие текстового информационного сообщения,
     * сопровождающее исключение:
     */
    @Test
    void checkMessage() {
        SimpleModel sm = new SimpleModel();
        String word = "Name";
        int number = 5;
        assertThatThrownBy(() -> sm.setName(word, number))
                /*проверяем класс исключения: */
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty();
    }

    /**
     * Так можно проверить содержание текстового информационного сообщения,
     * сопровождающее исключение:
     */
    @Test
    void checkWordMessage() {
        SimpleModel sm = new SimpleModel();
        String word = "Name";
        int number = 5;
        assertThatThrownBy(() -> sm.setName(word, number))
                /*проверяем классс исключения: */
                .isInstanceOf(IllegalArgumentException.class)
    /* с помощью регулярного выражения проверяем факт наличия сообщения */
                .hasMessageMatching("^.+")
    /* проверяем, что в сообщении есть соответствующие параметры: */
                .hasMessageContaining(word, number)
    /* проверяем наличие конкретного слова в сообщении: */
                .hasMessageContaining("Name");
    }

}