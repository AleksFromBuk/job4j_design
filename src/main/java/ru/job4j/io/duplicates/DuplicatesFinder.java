package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DuplicatesFinder {
    public static void main(String[] args) throws IOException {
        DuplicatesVisitor obj = new DuplicatesVisitor();
        Files.walkFileTree(Path.of("./"), obj);
        for (FileProperty it: obj.getMap().keySet()) {
            if (obj.getMap().get(it).size() > 1) {
                System.out.println("file name: " + it.getName() + ", file size: " + it.getSize());
                obj.getMap().get(it).forEach(System.out::println);
            }
        }
    }
}
