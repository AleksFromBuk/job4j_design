package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    public List<String> filter(String file) {
        List<String> data = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            StringBuilder str = new StringBuilder();
            data = in.lines()
                    .map(o -> o.split(" "))
                    .filter(arr -> "404".equals(arr[arr.length - 2]))
                    .map(arr -> (Arrays.stream(arr)
                                .reduce(str,
                                        (a, b) -> new StringBuilder(a).append(b).append(" "),
                                        StringBuilder::append)).toString())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            for (String it : log) {
                out.printf("%s%n", it);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter();
        List<String> log = logFilter.filter("data/log.txt");
        save(log, "data/404.txt");
    }
}