package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(target))
        )) {
            for (Path it : sources) {
                zip.putNextEntry(new ZipEntry(it.toFile().getPath()));
                zip.write(new BufferedInputStream(new FileInputStream(it.toFile())).readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(target))
        )) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(output.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();
        zip.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );
        ArgsName argsName = postValidateParam(args);
        List<Path> list = Search.search(
                Path.of(argsName.get("d")), obj -> !obj.endsWith(argsName.get("e"))
        );
        zip.packFiles(list, new File(argsName.get("o")));
    }

    private static ArgsName postValidateParam(String[] args) {
        final ArgsName argsName = ArgsName.of(args);
        String sourcePath = argsName.get("d");
        String extensionExcludedFiles = argsName.get("e");
        String targetInfo = argsName.get("o");
        if (!Files.exists(Path.of(sourcePath))) {
            throw new IllegalArgumentException("the selected folder does not exist");
        }
        if (!extensionExcludedFiles.endsWith(".class")) {
            throw new IllegalArgumentException("invalid extension of excluded files");
        }
        if (!targetInfo.endsWith(".zip")) {
            throw new IllegalArgumentException("the archive name has an incorrect extension");
        }
        return argsName;
    }
}