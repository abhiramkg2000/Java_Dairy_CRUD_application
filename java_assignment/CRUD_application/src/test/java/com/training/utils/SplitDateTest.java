package com.training.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplitDateTest {

    @Test
    void splitDate() {
        String date=SplitDate.splitDate("/api/cow/102?=2022/09/23");
        assertEquals("2022/09/23",date);
    }
}