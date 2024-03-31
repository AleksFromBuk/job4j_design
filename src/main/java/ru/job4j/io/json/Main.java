package ru.job4j.io.json;

import java.io.*;
import java.nio.file.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Person person = new Person(false, 30, new Contact("11-111"), new String[]{"Worker", "Married"});

        /*Запись объекта во временный файл, который удалиться системой*/
        File tempFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(person);
        }

        /*Чтение объекта из файла*/
        try (FileInputStream fis = new FileInputStream(tempFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            final Person personFromFile = (Person) ois.readObject();
            System.out.println("объект Person как результат сериализации: " + personFromFile + '\n');
        }
        /*Преобразуем объект person в json-строку*/
        final Gson gson = new GsonBuilder().create();
        System.out.println("результат преобразования объекта Person в JSON-строку: " + gson.toJson(person) + '\n');
        /*Создаем новую json-строку  с модифицированными данными*/
        final String personJSON =
                "{"
                + "\"sex\":false,"
                + "\"age\":35,"
                + "\"contact\":"
                + "{"
                + "\"phone\":\"+7(924)111-111-11\""
                + "},"
                + "\"statuses\":"
                + "[\"Student\", \"Free\"]"
                + "}";
        /*Превращаем json-строку обратно в объект */
        final Person personMod = gson.fromJson(personJSON, Person.class);
        System.out.println("обратный процесс: преобразование json-строки в объект Person: " + personMod);
    }
}
