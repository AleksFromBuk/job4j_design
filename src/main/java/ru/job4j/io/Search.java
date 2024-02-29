package ru.job4j.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Search {
    public static void main(String[] args) throws IOException {
        checkParam(args);
        Path start = Paths.get(args[0]);
        search(start, p -> p.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    private static void checkParam(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(System.lineSeparator()
                    + "1. Root folder is null. Usage  ROOT_FOLDER."
                    + System.lineSeparator()
                    + "2. enter the extension of the files you want to search");
        } else if (args.length == 1) {
            throw new IllegalArgumentException(System.lineSeparator()
                    + "The second command line argument required to specify file extensions is not specified");
        }
        if (!args[1].contains(".")) {
            args[1] = "." + args[1];
        }
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getList();
    }
}

class SearchFiles implements FileVisitor<Path> {
    private final Predicate<Path> conditional;
    private final List<Path> list = new ArrayList<>();

    SearchFiles(Predicate<Path> conditional) {
        this.conditional = conditional;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (conditional.test(file)) {
            list.add(file);
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return CONTINUE;
    }

    public List<Path> getList() {
        return list;
    }
}
