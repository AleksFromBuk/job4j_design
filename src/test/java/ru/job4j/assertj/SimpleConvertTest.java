package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> res = simpleConvert.toList("1", "2", "3", "4", "5");
        assertThat(res).hasSize(5)
                .allSatisfy(e -> {
                    assertThat(Integer.parseInt(e)).isLessThan(7);
                    assertThat(Integer.parseInt(e)).isGreaterThan(0);
                })
                .anySatisfy(e -> {
                    assertThat(Integer.parseInt(e)).isEqualTo(3);
                })
                .allMatch(e -> Integer.parseInt(e) < 10)
                .anyMatch(e -> Integer.parseInt(e) == 3)
                .noneMatch(e -> Integer.parseInt(e) < 1)
                .filteredOn(e -> Integer.parseInt(e) > 2).first().isEqualTo("3");
        assertThat(res).filteredOnAssertions(e -> assertThat(Integer.parseInt(e)).isLessThan(3))
                .hasSize(2)
                .contains("1", Index.atIndex(0))
                .doesNotContain("2", Index.atIndex(3));
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set  = simpleConvert.toSet("1", "2", "2", "3", "4", "5");
        assertThat(set).hasSize(5)
                .allSatisfy(e -> {
                    assertThat(Integer.parseInt(e)).isLessThan(7);
                    assertThat(Integer.parseInt(e)).isGreaterThan(0);
                })
                .anySatisfy(e -> {
                    assertThat(Integer.parseInt(e)).isEqualTo(3);
                })
                .allMatch(e -> Integer.parseInt(e) < 10)
                .anyMatch(e -> Integer.parseInt(e) == 3)
                .noneMatch(e -> Integer.parseInt(e) < 1)
                .filteredOn(e -> Integer.parseInt(e) > 2).first().isEqualTo("3");
        assertThat(set).filteredOnAssertions(e -> assertThat(Integer.parseInt(e)).isLessThan(3))
                .hasSize(2)
                .contains("1")
                .contains("2");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("1", "1", "2", "2", "3", "3", "3", "4", "5");
        assertThat(map).hasSize(5)
                .containsKeys("5", "4", "3", "2", "1")
                .containsValues(7, 4,  2, 0, 8)
                .containsExactlyInAnyOrderEntriesOf(Map.of("1", 0, "2", 2, "3", 4, "4", 7, "5", 8));
    }
}