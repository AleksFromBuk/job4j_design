package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
            List<String> data = in.lines()
                    .filter(str -> str.length() != 0 && str.charAt(0) != '#')
                    .toList();
            for (String it : data) {
                String[] tmp = processing(it);
                this.values.put(tmp[0], tmp[1]);
            }
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

    private static String[] processing(String str) throws IllegalArgumentException {
        String[] tmp = str.split("=", 2);
        if (tmp.length == 0 || tmp[0].length() == 0 || tmp[1].length() == 0) {
            throw new IllegalArgumentException("message");
        }
        return tmp;
    }

    public static void main(String[] args) {
        Config obj =  new Config("data/app.properties");
        obj.load();
        try {
            System.out.println(obj.value("hibernate.dialect"));
            System.out.println(obj.value("dialect"));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

}
