package com.example.wolfappremaster;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Format {

    private Format() {}


    private static final LinkedHashMap<String,FormatFunc> formats = new LinkedHashMap<>();

    public static void add(String order,FormatFunc func) {
        formats.put(order, func);
    }

    public static FormatFunc get(String order) {
        return formats.get(order);
    }


    public interface FormatFunc {
        String format(String speech, List<Character> characters);
    }

}
