package ru.job4j.io;

import java.io.*;
import java.util.List;

import static java.nio.charset.Charset.forName;

public class UsageEncoding {
    public String readFile(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, forName("WINDOWS-1251")))) {
            reader.lines()
                    .map(string -> string + System.lineSeparator())
                    .forEach(builder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void writeDataInFile(String path, List<String> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, forName("WINDOWS-1251"), true))) {
            data.forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\user\\IdeaProjects\\job4j_design\\data\\text.txt";
        UsageEncoding encoding = new UsageEncoding();
        List<String> strings = List.of(
                "����� ������ 1",
                "����� ������ 2",
                "����� ������ 3",
                "����� ������ 4",
                "����� ������ 5"
        );
        encoding.writeDataInFile(path, strings);
        String string = encoding.readFile(path);
        System.out.println("������ �� �����: ");
        System.out.println(string);
    }
}
