package com.training.validate;

import com.training.database.DatabaseOperations;
import com.training.model.Cow;
import com.training.model.CowMilk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Validation {
    public static boolean checkInsertCow(Connection connection, Cow cow) {
        Statement statement1;
        try
        {
            statement1 =connection.createStatement();
            String query1=String.format("SELECT govid FROM cows where govid=%d",cow.getGovId());

            ResultSet resultSet1=statement1.executeQuery(query1);

            if(!resultSet1.next())
            {
                    DatabaseOperations.insertCow(connection,cow);
                    System.out.println("cow data added");
                    return true;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkUpdateCow(Connection connection,Cow cow,int govId) {
        Statement statement1;
        try
        {
            statement1 =connection.createStatement();
            String query1=String.format("SELECT govid FROM cows where govid=%d",govId);

            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                DatabaseOperations.updateCow(connection,cow,govId);
                System.out.println("cow data updated");
                return true;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkInsertCowMilk(Connection connection, CowMilk cowMilk, int govId,String date) {
        Statement statement1;
        boolean check=false;
        try
        {
            statement1 =connection.createStatement();
            String query1=String.format("SELECT govid FROM cowmilk where govid=%d",govId);

            ResultSet resultSet1=statement1.executeQuery(query1);
            if(!resultSet1.next()||resultSet1.getInt("govid")==govId)
            {
                check=DatabaseOperations.insertCowMilk(connection,cowMilk,govId,date);
                //System.out.println("milk data added");
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return check;
    }

    public static boolean checkUpdateCowMilk(Connection connection,CowMilk cowMilk,int govId,String date) {
        Statement statement1;
        try
        {
            statement1 =connection.createStatement();
            String query1=String.format("SELECT govid FROM cowmilk where govid=%d AND date='%s'",govId,date);

            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                DatabaseOperations.updateCowMilk(connection,cowMilk,govId,date);
                System.out.println("milk data updated");
                return true;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkDeleteCow(Connection connection,int govId) {
        Statement statement1;
        try
        {
            statement1 =connection.createStatement();
            String query1=String.format("SELECT govid FROM cows where govid=%d",govId);

            ResultSet resultSet1=statement1.executeQuery(query1);

            if (resultSet1.next())
            {
                DatabaseOperations.deleteCow(connection,govId);
                System.out.println("delete called");
                return true;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkDateFormat(String date) {

        boolean check=false;
        if(date.matches("^(20)\\d{2}/(0[1-9]|1[0-2])/(0[1-9]|1\\d|2\\d|3[01])$"))
        {
            check=true;
        }
//        System.out.println("check: "+check);
        return check;
    }
}
