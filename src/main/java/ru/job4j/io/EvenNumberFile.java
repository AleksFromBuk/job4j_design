package ru.job4j.io;

import java.io.FileReader;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileReader in = new FileReader("data/even.txt")) {
            int buff;
            StringBuilder text = new StringBuilder();
            while ((buff = in.read()) != -1) {
                text.append((char) buff);
            }
            String[] data = text.toString().split("\r\n");
            for (String datum : data) {
                if (Integer.parseInt(datum) % 2 == 0) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
