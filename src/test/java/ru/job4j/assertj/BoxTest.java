package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isUnknownBox() {
        Box unknownBox = new Box(3, 10);
        String name = unknownBox.whatsThis();
        assertThat(name)
                .isEqualToIgnoringCase("unknown object")
                .contains("obj");
    }

    /**
     * public int getNumberOfVertices()
     */
    @Test
    void vertexIs() {
        Box box = new Box(3, -7);
        int numberOfVertex = box.getNumberOfVertices();
        assertThat(numberOfVertex).isEqualTo(-1);
    }

    @Test
    void typeDependencyOnVertex() {
        Box box = new Box(3, -7);
        String type = "Unknown";
        assertThat(box).matches(b -> b.getNumberOfVertices() == -1 && box.whatsThis().contains(type));
    }

    /**
     * public boolean isExist()
     */
    @Test
    void isExistFirst() {
        Box box = new Box(4, 1);
        String type = "Sphere" + "Tetrahedron" + "Cube";
        assertThat(box).matches(b -> b.isExist() && type.contains(box.whatsThis()));
    }

    @Test
    void isExistSecond() {
        Box box = new Box(4, 0);
        String type = "Unknown";
        assertThat(box).matches(b -> !b.isExist() && box.whatsThis().contains(type));
    }

    /**
     *  public double getArea()
     */
    @Test
    void doubleTypePropertiesFirst() {
        Box box = new Box(0, 1);
        double res = box.getArea();
        assertThat(res).isEqualTo(12d, within(0.57d))
                        .isCloseTo(13d, withPrecision(0.49d))
                        .isGreaterThan(12.56d)
                        .isLessThan(12.57d);
    }

    @Test
    void doubleTypePropertiesSecond() {
        Box box = new Box(4, 1);
        double res = box.getArea();
        assertThat(res).isEqualTo(1.73205d, within(0.000001d))
                .isCloseTo(2d, withPrecision(0.3d))
                .isGreaterThan(1.7d)
                .isLessThan(1.7321d);
    }
}