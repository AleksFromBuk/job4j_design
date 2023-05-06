package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

class AnalysisTest {
    @Test
    void unavailable1(@TempDir Path tempDir) throws IOException {
        File target = tempDir.resolve("server.log").toFile();
        try (PrintWriter out = new PrintWriter(target)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("300 10:59:01");
            out.println("500 11:01:02");
            out.println("200 11:02:02");
        }
        File source = tempDir.resolve("target.csv").toFile();
        Analysis obj = new Analysis();
        obj.unavailable(target.getAbsolutePath(), source.getAbsolutePath());

        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            in.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString())
                .isEqualTo("10:57:01;10:59:01;11:01:02;11:02:02;");
    }

    @Test
    void unavailable2(@TempDir Path tempDir) throws IOException {
        File target = tempDir.resolve("server.log").toFile();
        try (PrintWriter out = new PrintWriter(target)) {
            out.println("200 10:56:01");
            out.println("500 10:57:01");
            out.println("400 10:58:01");
            out.println("500 10:59:01");
            out.println("400 11:01:02");
            out.println("300 11:02:02");
        }
        File source = tempDir.resolve("target.csv").toFile();
        Analysis obj = new Analysis();
        obj.unavailable(target.getAbsolutePath(), source.getAbsolutePath());

        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            in.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString())
                .isEqualTo("10:57:01;11:02:02;");
    }

}