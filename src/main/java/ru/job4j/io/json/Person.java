package ru.job4j.io.json;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class Person implements Serializable {
    private final static Long serialVersionUID = 1L;
    private final boolean sex;
    private final int age;
    private final Contact contact;
    private final String[] statuses;

    public Person(boolean sex, int age, Contact contact, String[] statuses) {
        this.sex = sex;
        this.age = age;
        this.contact = contact;
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return "Person{"
                + "sex=" + sex
                + ", age=" + age
                + ", contact=" + contact
                + ", statuses=" + Arrays.toString(statuses)
                + '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact = new  Contact("+7 (111) 111 11 11");
        final Person person = new Person(false, 30, contact, new String[] {"Worker", "Married"});

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
            System.out.println(personFromFile);
        }
    }
}