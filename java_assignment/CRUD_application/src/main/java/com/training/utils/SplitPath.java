package com.training.utils;

public class SplitPath {

    //Function to parse path parameter
    public static String splitPath(String query) {
        if (query == null || "".equals(query)) {
            return "0";
        }
        String [] pathParameter =query.split("/");
        return pathParameter[3];
    }
}
