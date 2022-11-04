package com.training.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SplitMonth {

    //Function to parse query month parameter
//    public static String splitMonth(String query) {
//        if (query == null || "".equals(query)) {
//            return "0";
//        }
//        String [] pathParameter =query.split("=");
//        return pathParameter[1];
//    }
    public static Map<String,String> splitMonth(String uri) {

        //If there is no URI return empty map.
        if (uri == null || "".equals(uri)) {
            return Collections.emptyMap();
        }

        Pattern pattern = Pattern.compile("&");
        Stream<String> parameterStream = pattern.splitAsStream(uri);
        Stream<String[]> splitParameterStream = parameterStream.map(
                s -> Arrays.copyOf(s.split("="),2));
        Map<String,String> parameters = splitParameterStream.collect(Collectors.toMap(
                strings -> strings[0],strings -> strings[1]));
        return parameters;
    }
}
