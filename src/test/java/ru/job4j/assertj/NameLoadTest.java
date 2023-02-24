package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.*;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkPartOfParseMethod() {
        NameLoad nl = new NameLoad();
        assertThatThrownBy(nl::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is empty");
    }

    @Test
    void checkFirstExcOfValidate() {
        NameLoad nl = new NameLoad();
        String names = "key:value";
        assertThatThrownBy(() -> nl.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(names)
                .hasMessageContaining("\"=\"")
                .hasMessageNotContaining("\"%s\"");
    }

    @Test
    void checkSecondExcValidate() {
        NameLoad nl = new NameLoad();
        String names = "=value";
        assertThatThrownBy(() -> nl.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty()
                .contains(names)
                .contains("key");
    }

    @Test
    void checkThridExcValidate() {
        NameLoad nl = new NameLoad();
        String names = "Ы=";
        assertThatThrownBy(() -> nl.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .message()
                .isNotEmpty()
                .contains(names)
                .contains("value");
    }
}