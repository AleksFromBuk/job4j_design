package ru.job4j.io;

import java.io.*;

public class Buffered {
    public static void main(String[] args) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("data/newData.txt"));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("data/output.txt", true))) {
            out.write(in.readAllBytes());
            /*out.write("bugaga".getBytes());*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
