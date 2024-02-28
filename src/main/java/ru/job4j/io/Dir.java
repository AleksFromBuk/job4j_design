package ru.job4j.io;

import java.io.File;
import java.util.Objects;

public class Dir {
    public static void main(String[] args) {
        File file = new File("c:\\users\\user\\IdeaProjects");
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsoluteFile()));
        }
        System.out.printf("size : %d\n", file.getTotalSpace());
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            System.out.printf("name and size of FileObject: %s, %d" + System.lineSeparator(), subFile.getName(), subFile.length());
        }
    }

}
