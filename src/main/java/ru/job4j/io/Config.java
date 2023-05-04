package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() throws IllegalArgumentException {
        try (BufferedReader in = new BufferedReader(new FileReader(this.path))) {
            in.lines()
                    .filter(str -> !str.isBlank() && !str.startsWith("#"))
                    .map(str -> str.split("=", 2))
                    .filter(Config::checkProcessing)
                    .forEach(arr -> this.values.put(arr[0], arr[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) throws UnsupportedOperationException {
        if (!this.values.containsKey(key)) {
            throw new UnsupportedOperationException("Don't impl this method yet!");
        }
        return this.values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    private static boolean checkProcessing(String[] str) throws IllegalArgumentException {
        if (str.length != 2 || str[0].isBlank() || str[1].isBlank()) {
            throw new IllegalArgumentException("".equals(str[0] + str[1]) ? "string isBlank" : str[0] + str[1]);
        }
        return true;
    }

    public static void main(String[] args) {
        Config obj = new Config("data/app.properties");
        obj.load();
        try {
            System.out.println(obj.value("hibernate.dialect"));
            System.out.println(obj.value("dialect"));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

}