package ru.job4j.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.charset.Charset.forName;

public class CSVReader {
    private static final String PATH = "path";
    private static final String DELIMITER = "delimiter";
    private static final String OUT = "out";
    private static final String FILTER = "filter";

    public static void handle(ArgsName argsName) throws Exception {
        try (var reader = Files.newBufferedReader(Path.of(argsName.get(PATH)), forName("WINDOWS-1251"));
             var scan = new Scanner(reader)) {
            List<String> data = new ArrayList<>();
            String delimiter = argsName.get(DELIMITER);
            String titleDataLine = scan.nextLine();
            String[] tmpForDefinitionIndexKeWords = titleDataLine.split(delimiter);
            List<Integer> indexes = new ArrayList<>();
            String[] filters = argsName.get(FILTER).split(",");
            for (int i = 0; i < filters.length; i++) {
                filters[i] = filters[i].replace(" ", "");
            }
            for (String it : filters) {
                for (int i = 0; i < tmpForDefinitionIndexKeWords.length; i++) {
                    if (Objects.equals(it, tmpForDefinitionIndexKeWords[i])) {
                        indexes.add(i);
                        break;
                    }
                }
            }

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < filters.length - 1; i++) {
                builder.append(filters[i]).append(delimiter);
            }
            builder.append(filters[filters.length - 1]).append(System.lineSeparator());
            data.add(builder.toString());
            builder.delete(0, builder.length());

            while (scan.hasNextLine()) {
                String[] tmp = scan.nextLine().split(delimiter);
                for (int i = 0; i < indexes.size() - 1; i++) {
                    builder.append(tmp[indexes.get(i)]).append(delimiter);
                }
                builder.append(tmp[indexes.get(indexes.size() - 1)]).append(System.lineSeparator());
                data.add(builder.toString());
                builder.delete(0, builder.length());
            }
            saveDataCSV(data, argsName.get(OUT));
            builder.delete(0, builder.length());
        }
    }

    private static void saveDataCSV(List<String> data, String s) {
        try (var writer = new PrintWriter(new FileWriter(s, forName("WINDOWS-1251"), true))) {
            data.forEach(writer::print);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArgsName validateParams(String[] args) {
        final ArgsName argsName = ArgsName.of(args);
        String sourceTarget = argsName.get(OUT);
        if (!("stdout".equals(sourceTarget) || Files.exists(Path.of(sourceTarget)) || sourceTarget.contains("Temp"))) {
            throw new IllegalArgumentException("parameters for outputting file contents are set incorrectly");
        }
        String delimiter = argsName.get(DELIMITER);
        if (!(delimiter.contains(";") || delimiter.contains(",") || delimiter.contains(":"))) {
            throw new IllegalArgumentException("invalid format for column delimiter...");
        }
        String sourcePath = argsName.get(PATH);
        if (!Files.exists(Path.of(sourcePath))) {
            throw new IllegalArgumentException("the selected folder does not exist");
        }
        /*
         * блок проверки на наличие фильтр-слов в строке-заголовке файла -
         * если хотя бы один фильтр содержится в первой строке файла, считаем
         * содержимое по фильтру корректным
         */
        try (var reader = Files.newBufferedReader(Path.of(sourcePath), forName("WINDOWS-1251"));
             var scan = new Scanner(reader)) {
            boolean checkFilterWords = true;
            String[] tmpFilters = argsName.get(FILTER).split(",");
            List<String> titleDataInSourcePath = Arrays
                    .stream(scan.nextLine().split(delimiter))
                    .toList();
            for (String it : tmpFilters) {
                if (titleDataInSourcePath.contains(it)) {
                    checkFilterWords = false;
                    break;
                }
            }
            if (checkFilterWords) {
                throw new IllegalArgumentException("the file does not contain columns with the names passed in "
                        + "the parameters");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return argsName;
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = validateParams(args);
        handle(argsName);
    }
}
