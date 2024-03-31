package ru.job4j.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class MyExample implements Serializable {
    private final static Long serialVersionUID = 1L;
    private final boolean flag;
    private final int number;
    private final String str;
    private final Person person;
    private final Contact[] contacts;

    public MyExample(boolean flag, int number, String str, Person person, Contact[] contacts) {
        this.flag = flag;
        this.number = number;
        this.str = str;
        this.person = person;
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "MyExample{"
                + "flag=" + flag
                + ", number=" + number
                + ", str=" + str
                + ", Person=" + person
                + ", contacts=" + Arrays.toString(contacts)
                + "}";
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact1 = new Contact("11-111");
        final Contact contact2 = new Contact("22-222");
        final Person person = new Person(true, 31, new Contact("33-333"), new String[]{"Worker", "Married"});
        final MyExample myExample = new MyExample(true, 33, "String", person, new Contact[]{contact1, contact2});

        /*Запись объекта во временный файл, который удалиться системой*/
        File tempFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(myExample);
        }

        /*Чтение объекта из файла*/
        try (FileInputStream fis = new FileInputStream(tempFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            final MyExample myExampleFromFile = (MyExample) ois.readObject();
            System.out.println("\nобъект MyExample как результат сериализации: " + myExampleFromFile + '\n');
        }

        /*Преобразуем объект person в json-строку*/
        final Gson gson = new GsonBuilder().create();
        System.out.println("результат преобразования объекта MyExample в JSON-строку: " + gson.toJson(myExample) + '\n');
        /*Создаем новую json-строку с модифицированными данными*/
        final String myExampleJSON =
                "{"
                        + "\"flag\":false,"
                        + "\"number\":35,"
                        + "\"str\":\"current value of String\","
                        + "\"person\":"
                        + "{"
                        + "\"sex\":true,"
                        + "\"age\":31,"
                        + "\"contact\":"
                        + "{"
                        + "\"phone\":\"22-222\""
                        + "},"
                        + "\"statuses\":"
                        + "[\"Worker\", \"Married\"]"
                        + "},"
                        + "\"contacts\":"
                        + "["
                        + "{"
                        + "\"phone\":\"11-111\""
                        + "},"
                        + "{"
                        + "\"phone\":\"22-222\""
                        + "}"
                        + "]"
                        + "}";
        /*Превращаем json-строку обратно в объект */
        final MyExample myExampleMod = gson.fromJson(myExampleJSON, MyExample.class);
        System.out.println("обратный процесс: преобразование json-строки в объект Person: " + myExampleMod);
    }
}
