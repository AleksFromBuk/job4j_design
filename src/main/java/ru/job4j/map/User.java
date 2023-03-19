package ru.job4j.map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public static void main(String[] args) {
        java.util.Map<User, Object> map = new HashMap<User, Object>();
        User user1 = new User("qwerty", 1, Calendar.getInstance());
        map.put(user1, new Object());
    }
}
