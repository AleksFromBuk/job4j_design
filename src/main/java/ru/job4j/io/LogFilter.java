package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                                        (a, b) -> new StringBuilder(a).append(" ").append(new StringBuilder(b)),
                                        (a, b) -> a.append(b))).toString())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter();
        List<String> log = logFilter.filter("data/log.txt");
        for (String it : log) {
            System.out.println(it);
        }
    }
}