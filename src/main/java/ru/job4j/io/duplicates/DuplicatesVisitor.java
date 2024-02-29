package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private Map<FileProperty, List<String>> map = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        map.computeIfAbsent(new FileProperty(file.toFile().length(), file.toFile().getName()),
                k -> new ArrayList<>()).add(file.toAbsolutePath().toString());
        return super.visitFile(file, attributes);
    }

    public Map<FileProperty, List<String>> getMap() {
        return map;
    }
}
