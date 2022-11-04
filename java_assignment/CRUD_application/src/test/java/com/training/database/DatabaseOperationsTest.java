package com.training.database;

import com.training.model.Cow;
import com.training.utils.SplitDate;
import com.training.utils.SplitPath;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseOperationsTest {

    @Test
    void viewCows() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        List <Cow> check= DatabaseOperations.viewCows(con);
        assertFalse(check.isEmpty());
    }

    @Test
    void viewCow() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        List <Cow> check= DatabaseOperations.viewCow(con,Integer.parseInt(SplitPath.splitPath("/api/cow/103")));
        assertFalse(check.isEmpty());
    }

    @Test
    void viewTotalProfit() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        int totalProfit= DatabaseOperations.viewTotalProfit(con,"2022/09/30","10");
        assertEquals(460,totalProfit);
    }

    @Test
    void readCowMilkDate() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        int check= DatabaseOperations.readCowMilkDate(con, SplitDate.splitDate("/api/cowmilk/101?=2022/09/23"),Integer.parseInt(SplitPath.splitPath("/api/cow/101")));
        assertEquals(20,check);
    }

    @Test
    void viewTotalCowMilkDate() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        int total_milk_in_a_date= DatabaseOperations.viewTotalCowMilkDate(con, SplitDate.splitDate("/api/milk/?date=2022/09/23"));
        assertEquals(40,total_milk_in_a_date);
    }

    @Test
    void viewTotalMilk() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        int check=DatabaseOperations.viewTotalMilk(con,"2022/09/30");
        assertEquals(100,check);
    }
}