package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {
    public static Info diff(Set<User> previous, Set<User> current) {
        Info res = new Info();
        Map<Integer, User> map = new HashMap<>();
        /**
         * заполняем map - O(previous.size())
         */
        for (User it : previous) {
            map.put(it.getId(), it);
        }
        /**
         *   считаем количетсва изменённых и добавленных-новых
         *   - обход current( O(current.size()) ) + методы contains и проверки( O(1) )
         *   - в итоге получим O(current.size())
         */
        changedAndAdded(previous, current, map, res);
         /**
         *   Считаем количество удаленных:
         *   обход previous - O(previous.size()) + метод contains и эелементарные
         *   математические операции ( O(1) )
         *   - в итоге O(previous.size())
         */
        deleted(previous, current, res);
        /**
         *  общая асимптотика решения O(previous.size() + current.size())
         */
        return res;
    }

    public static void changedAndAdded(Set<User> previous, Set<User> current, Map<Integer, User> map, Info inf) {
        for (User it : current) {
            if (!previous.contains(it)) {
                if (map.containsKey(it.getId())) {
                    inf.setChanged(inf.getChanged() + 1);
                } else {
                    inf.setAdded(inf.getAdded() + 1);
                }
            }
        }
    }

    public static void deleted(Set<User> previous, Set<User> current, Info inf) {
        for (User it : previous) {
            if (!current.contains(it)) {
                inf.setDeleted(inf.getDeleted() + 1);
            }
        }
        inf.setDeleted(inf.getDeleted() - inf.getChanged());
    }
}