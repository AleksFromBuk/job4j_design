package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {
    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        int step = 0;
        while (source.hasNext()) {
            nodes.get(step++).add(source.next());
            step = step == nodes.size() ? 0 : step;
        }
    }
}
