package ru.job4j.io.serialization.json;

import org.json.JSONObject;

public class JsonObject {
    public static void main(String[] args) {
        final Person person = new Person(false, 31, new Contact("11-111"), "Worker", "Married");
        Contact contact2 = new Contact("33-777");
        Contact contact3 = new Contact("77-333");
        final MyExample myExample = new MyExample(true, 41, "current string value", person,
                new Contact[]{contact2, contact3});
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("flag", myExample.isFlag());
        jsonObject2.put("number", myExample.getNumber());
        jsonObject2.put("str", myExample.getStr());
        jsonObject2.put("person", person);
        jsonObject2.put("contacts", myExample.getContacts());
        /* преобразование объекта MyExample в JSONObject */
        System.out.println(jsonObject2);
        System.out.println(System.lineSeparator());
        /* Преобразуем объект person в json-строку */
        System.out.println(new JSONObject(myExample));
    }
}
