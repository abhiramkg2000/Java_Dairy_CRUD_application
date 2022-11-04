package com.training.validate;

import com.training.database.DatabaseConnection;
import com.training.model.Cow;
import com.training.model.CowMilk;
import com.training.utils.SplitDate;
import com.training.utils.SplitPath;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void checkInsertCow() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        Cow cow=new Cow();
        cow.setId(4);
        cow.setAge(5);
        cow.setColor("orange");
        cow.setGender("female");
        cow.setMilking("true");
        cow.setGovId(105);
        cow.setExpenditure(200);
        boolean check=Validation.checkInsertCow(con,cow);
        assertTrue(check);
    }

    @Test
    void checkUpdateCow() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        Cow cow=new Cow();
        cow.setId(1);
        cow.setAge(5);
        cow.setColor("orange");
        cow.setGender("female");
        cow.setMilking("true");
        cow.setGovId(102);
        cow.setExpenditure(200);
        boolean check=Validation.checkUpdateCow(con,cow, Integer.parseInt(SplitPath.splitPath("/api/cow/102")));
        assertTrue(check);
    }

    @Test
    void checkInsertCowMilk() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        CowMilk cowMilk=new CowMilk();
        cowMilk.setMilkProducedDaily(20);
        boolean check=Validation.checkInsertCowMilk(con,cowMilk,Integer.parseInt(SplitPath.splitPath("/api/cowmilk/101")),SplitDate.splitDate("/api/cowmilk/101?=2022/09/30"));
        assertTrue(check);
    }

    @Test
    void checkUpdateCowMilk() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        CowMilk cowMilk=new CowMilk();
        cowMilk.setMilkProducedDaily(20);
        boolean check=Validation.checkUpdateCowMilk(con,cowMilk,Integer.parseInt(SplitPath.splitPath("/api/cowmilk/101")), SplitDate.splitDate("/api/cowmilk/101?=2022/09/30"));
        assertTrue(check);
    }

    @Test
    void checkDeleteCow() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        boolean check=Validation.checkDeleteCow(con,Integer.parseInt(SplitPath.splitPath("/api/cow/102")));
        assertTrue(check);
    }

    @Test
    void checkDateFormat() {
        String date=SplitDate.splitDate("/api/profit?date=2022/10/03");
        boolean checkDateFormat=Validation.checkDateFormat(date);
        assertTrue(checkDateFormat);
    }
}