package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static java.nio.charset.Charset.forName;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private Integer answerCounter = 0;
    private final List<String> answers = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        try (var userReader = new BufferedReader(new InputStreamReader(System.in))) {
            readPhrases();
            String currentWord = null, currentAnswer;
            List<String> commonLog = new ArrayList<>();
            int i = 0;

            Consumer<String> addToLog = message -> {
                commonLog.add(message);
                commonLog.add(System.lineSeparator());
            };

            while (!OUT.equals(currentWord)) {
                currentWord = userReader.readLine();
                addToLog.accept(currentWord);
                if (STOP.equals(currentWord)) {
                    addToLog.accept(STOP);
                    while (!CONTINUE.equals(currentWord)) {
                        currentWord = userReader.readLine();
                        addToLog.accept(currentWord);
                    }
                    currentAnswer = answers.get(i++);
                } else {
                    currentAnswer = answers.get(i++);
                    readPhrases();
                }
                System.out.println(currentAnswer);
                addToLog.accept(currentAnswer);
                if (i == answers.size()) {
                    readPhrases();
                }
            }
            saveLog(commonLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dataLoaderForAnswers() {
        List<String> dataTmp = IntStream.range(answerCounter, answerCounter + 3)
                .mapToObj(i -> "bot answer number: " + i + System.lineSeparator())
                .toList();
        try (var writer = new PrintWriter(new FileWriter(botAnswers, forName("WINDOWS-1251"), true))) {
            dataTmp.forEach(writer::print);
        } catch (IOException b) {
            b.printStackTrace();
        }
        answerCounter += 3;
    }

    private void readPhrases() {
        List<String> tmp = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(botAnswers), forName("WINDOWS-1251"))) {
            long forDataSkip = Files.size(Path.of(botAnswers));
            reader.skip(forDataSkip);
            dataLoaderForAnswers();
            String line;
            while ((line = reader.readLine()) != null) {
                tmp.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        answers.addAll(tmp);
    }

    private void saveLog(List<String> log) {
        try (var writer = new PrintWriter(new FileWriter(path, forName("WINDOWS-1251"), true))) {
            log.forEach(writer::print);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ru.job4j.io.ConsoleChat consoleChat = new ru.job4j.io.ConsoleChat("C:\\Users\\user\\IdeaProjects\\job4j_design\\data\\commonLogOfChatBot.txt",
                "C:\\Users\\user\\IdeaProjects\\job4j_design\\data\\sourceDataForChatBot.txt");
        consoleChat.run();
    }
}