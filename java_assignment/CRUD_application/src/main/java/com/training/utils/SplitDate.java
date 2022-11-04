package com.training.utils;

public class SplitDate {

    //Function to parse query date parameter
    public static String splitDate(String query) {
        if (query == null || "".equals(query)) {
            return "0";
        }
        String [] pathParameter =query.split("=");
        return pathParameter[1];
    }
}
