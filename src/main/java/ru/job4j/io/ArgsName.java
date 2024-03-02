package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("This key: '" + key + "' is missing");
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        for (String it : args) {
            String[] tmpIt =  it.substring(1).split("=", 2);
            values.put(tmpIt[0], tmpIt[1]);
        }
    }

    private static void dataValidation(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        for (String it : args) {
            if (it.charAt(0) != '-') {
                throw new IllegalArgumentException("Error: This argument '" + it + "' does not start with a '-' character");
            } else if (!it.contains("=")) {
                throw new IllegalArgumentException("Error: This argument '" + it + "' does not contain an equal sign");
            }
            String tmp = it.substring(1);
            String[] tmpIt = tmp.split("=", 2);
            if (tmpIt[0].length() == 0) {
                throw new IllegalArgumentException("Error: This argument '" + it + "' does not contain a key");
            } else if (tmpIt.length == 1 || "".equals(tmpIt[1])) {
                throw new IllegalArgumentException("Error: This argument '" + it + "' does not contain a value");
            }
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        dataValidation(args);
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[]{"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));
        ArgsName zip = ArgsName.of(new String[]{"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}
