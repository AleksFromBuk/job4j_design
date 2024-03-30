package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;

public class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int zipCode;
    private final String phone;
    private transient final String tmp;

    public Contact(int zipCode, String phone, String tmp) {
        this.zipCode = zipCode;
        this.phone = phone;
        this.tmp = tmp;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "zipCode=" + zipCode
                + ", phone='" + phone + '\''
                + ", tmp=" + tmp
                + '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact = new Contact(1234567, "+7 (111) 111 11 11", "asdasd");

        /*Запись объекта во временный файл, который удалиться системой*/
        File tempFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(contact);
        }

        /*Чтение объекта из файла*/
        try (FileInputStream fis = new FileInputStream(tempFile);
        ObjectInputStream ois = new ObjectInputStream(fis)) {
            final Contact contactFromFile = (Contact) ois.readObject();
            System.out.println(contactFromFile);
        }
    }
}
