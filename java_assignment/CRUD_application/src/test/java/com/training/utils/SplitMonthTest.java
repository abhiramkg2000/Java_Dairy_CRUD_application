package com.training.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SplitMonthTest {

    @Test
    void splitMonth() {
        Map<String,String> map=SplitMonth.splitMonth("date=2022/09/30&month=10");
        //System.out.println(map.entrySet());
        assertEquals("2022/09/30",map.get("date"));
        assertEquals("10",map.get("month"));
    }
}