package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {
    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        int step = 0;
        int div = nodes.size();
        while (source.hasNext()) {
            nodes.get(step++).add(source.next());
            step = step == div ? 0 : step;
        }
    }
}
