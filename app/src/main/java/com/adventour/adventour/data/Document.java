package com.adventour.adventour.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public abstract class Document extends HashMap<String, Object> {

    public static ArrayList<Integer> convertFromInt(HashMap<String, Integer> m) {
        ArrayList<Integer> l = new ArrayList<>();
        Set<Entry<String, Integer>> s = m.entrySet();
        for (Entry<String, Integer> e : s) {
            l.add(Integer.parseInt(e.getKey()), e.getValue());
        }

        return l;
    }

    public static ArrayList<Long> convertFromLong(HashMap<String, Long> m) {
        ArrayList<Long> l = new ArrayList<>();
        Set<Entry<String, Long>> s = m.entrySet();
        for (Entry<String, Long> e : s) {
            l.add(Integer.parseInt(e.getKey()), e.getValue());
        }

        return l;
    }

    public static Pair<Double> convertFromPairDouble(HashMap<String, Double> m) {
        Pair<Double> p = new Pair<>((double) m.get("first"), (double) m.get("second"));

        return p;
    }


    public static Pair<Double> convertFromPairLong(HashMap<String, Long> m) {
        Pair<Double> p = new Pair<>((double) m.get("first"), (double) m.get("second"));

        return p;
    }

}
