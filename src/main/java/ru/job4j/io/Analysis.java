package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            final boolean[] f = {false};
            in.lines()
                    .map(str -> str.split(" "))
                    .forEach(arr -> {
                        if (!f[0] && processOpen.test(arr[0])) {
                            f[0] = true;
                            save(target, arr[1] + ";");
                        } else if (f[0] && processClose.test(arr[0])) {
                            f[0] = false;
                            save(target, arr[1] + ";" + System.lineSeparator());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Predicate<String> processOpen = s -> "400".equals(s) || "500".equals(s);

    private static Predicate<String> processClose = s -> "200".equals(s) || "300".equals(s);

    private static void save(String target, String data) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(
                new FileOutputStream(target, true)))) {
            out.print(data);
        } catch (IOException o) {
            o.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
        analysis.unavailable("data/server2.log", "data/target2.csv");
    }
}
