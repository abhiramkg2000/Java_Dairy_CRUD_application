package com.training.repository;

import com.training.model.Cow;

import java.sql.ResultSet;

public class AllCows {
    public static Cow readCows(ResultSet resultSet1) {
        Cow cow = new Cow();
        try {
            cow.setId(resultSet1.getInt("id"));
            cow.setAge(resultSet1.getInt("age"));
            cow.setColor(resultSet1.getString("color"));
            cow.setGender(resultSet1.getString("gender"));
            cow.setMilking(resultSet1.getString("milking"));
            cow.setGovId(resultSet1.getInt("govid"));
            cow.setExpenditure(resultSet1.getInt("expenditure"));
        }catch (Exception e) {
            System.out.println(e);
        }
        return cow;
    }
}
