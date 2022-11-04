package com.training.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SplitPathTest {

    @Test
    void splitPath() {
        String id=SplitPath.splitPath("/api/cow/102");
        assertEquals("102",id);
    }
}