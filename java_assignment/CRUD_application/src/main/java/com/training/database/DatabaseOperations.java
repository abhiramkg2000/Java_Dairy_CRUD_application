package com.training.database;

import com.training.model.Cow;
import com.training.model.CowMilk;
import com.training.repository.AllCows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    static int uniqueId1=0;
    static int uniqueId2=0;
    static int uniqueId3=0;
    static int totalPurchasedMilkQuantity=0;
    static int totalPurchasedMilkPrice=0;

    public static int getTotalPurchasedMilkQuantity() {
        return totalPurchasedMilkQuantity;
    }

    public static int getTotalPurchasedMilkPrice() {
        return totalPurchasedMilkPrice;
    }

    public static void insertCow(Connection connection, Cow cow) {

        Statement statement1;
        try {
            statement1 = connection.createStatement();

            String query1 = String.format("INSERT INTO cows values" +
                            "(%d,%d,'%s','%s','%s',%d,%d)",
                    ++uniqueId1, cow.getAge(), cow.getColor(),
                    cow.getGender(), cow.getMilking(),cow.getGovId(),cow.getExpenditure());

            statement1.executeUpdate(query1);
            //System.out.println("cow inserted successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        totalExpenditureCalculate(connection);
    }

    public static void updateCow(Connection connection, Cow cow,int govId) {

        Statement statement1;
        try {
            statement1 = connection.createStatement();

            String query1 = String.format("UPDATE cows SET age=%d,color='%s',gender='%s',milking='%s',govid=%d,expenditure=%d WHERE govid=%d",
                    cow.getAge(), cow.getColor(),
                    cow.getGender(), cow.getMilking(),govId,cow.getExpenditure(),govId);

            statement1.executeUpdate(query1);
            //System.out.println("cow updated successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        totalExpenditureCalculate(connection);
    }

    public static boolean insertCowMilk(Connection connection, CowMilk cowMilk, int govId,String date) {

        Statement statement1;

//        //To Format Date
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDateTime now = LocalDateTime.now();
//        String date=dtf.format(now);
        try {
            statement1 = connection.createStatement();

            String query1 = String.format("INSERT INTO cowmilk values" +
                            "(%d,%d,'%s',%d)", ++uniqueId2,govId,date,cowMilk.getMilkProducedDaily());
            try
            {
                statement1.executeUpdate(query1);
                totalMilkCalculation(connection);
                totalExpenditureCalculate(connection);
                return true;
            }catch (Exception e) {
                System.out.println("error:\n "+e);
                return false;
            }
            //System.out.println("milk inserted successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    //To get milk produced by a specific cow in a specific date
    public static int readCowMilkDate(Connection connection, String date,int govId) {

        Statement statement1;
        try {
            statement1 = connection.createStatement();
            ResultSet resultSet1;
            //System.out.println("param2: "+param2);
            //Reading milk of cow with specific id and date from cow milk table.
            if(date.equals("0"))
            {
                resultSet1 = statement1.executeQuery(String.format("SELECT sum(milk_produced_daily) as sum FROM cowmilk WHERE govid=%d",govId));
                while (resultSet1.next()) {
                    return resultSet1.getInt("sum");
                }
            }
            else
            {
                resultSet1 = statement1.executeQuery(String.format("SELECT milk_produced_daily FROM cowmilk WHERE date='%s' AND govid=%d", date,govId));
                while (resultSet1.next()) {
                    return resultSet1.getInt("milk_produced_daily");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("read cow milk date successfully");
        return 0;
    }

    public static void updateCowMilk(Connection connection,CowMilk cowMilk,int govId,String date) {

        Statement statement1;
        try {
            statement1 = connection.createStatement();

            String query1 = String.format("UPDATE cowmilk SET milk_produced_daily=%d WHERE govid=%d AND date='%s'",
                    cowMilk.getMilkProducedDaily(),govId,date);

            statement1.executeUpdate(query1);
            //System.out.println("cow milk updated successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        totalMilkCalculation(connection);
    }

    public static void deleteCow(Connection connection, int govId) {

        Statement statement1,statement2;
        try {
                statement1 = connection.createStatement();
                statement2 = connection.createStatement();

                String query1=String.format("DELETE FROM cowmilk WHERE govid=" + "%d", govId);
                statement1.executeUpdate(query1);
                String query2= String.format("DELETE FROM cows WHERE govid=" + "%d", govId);
                statement2.executeUpdate(query2);
        } catch (Exception e) {
            System.out.println(e);
        }
        totalMilkCalculation(connection);
    }

    public static List<Cow> viewCows(Connection connection) {

        Statement statement1;
        List<Cow> arraylist = new ArrayList<>();
        try {
            statement1 = connection.createStatement();

            //Reading all cows from cows table.
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM cows");

            while (resultSet1.next()) {
                arraylist.add(AllCows.readCows(resultSet1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("cows Read successful");
        return arraylist;
    }

    public static List<Cow> viewCow(Connection connection, int govId) {

        Statement statement1;
        List<Cow> arraylist=new ArrayList<>();
        try {
            statement1 = connection.createStatement();

            //Reading a cow from cows table.
            ResultSet resultSet1 = statement1.executeQuery(String.format("SELECT * FROM cows WHERE govid=%d", govId));

            while (resultSet1.next()) {
                arraylist.add(AllCows.readCows(resultSet1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("cow Read successful");
        return arraylist;
    }

    //To get total milk produced by all cows in a specific date
    public static int viewTotalCowMilkDate(Connection connection, String date) {

        Statement statement1;
        int total_milk_in_a_date=0;
        try {
            statement1 = connection.createStatement();

            //Reading all cows from cows table.
            ResultSet resultSet1 = statement1.executeQuery(String.format("SELECT sum(milk_produced_daily) as total_milk_in_a_date FROM cowmilk WHERE date='%s'",date));

            while (resultSet1.next()) {
               total_milk_in_a_date=resultSet1.getInt("total_milk_in_a_date");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("view total cow milk date successfully");
        return total_milk_in_a_date;
    }

    public static int viewTotalMilk(Connection connection,String date) {

        //totalMilkCalculation(connection);
        Statement statement1;
        int totalMilk=0;
        try {
            statement1 = connection.createStatement();
            String query1=String.format("SELECT total_milk_remaining FROM ledger WHERE date='%s'",date);
            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                totalMilk=resultSet1.getInt("total_milk_remaining");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return totalMilk;
    }

    public static void totalMilkCalculation(Connection connection) {

        Statement statement1,statement2;

        //To Format Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);

        try {
            statement1 = connection.createStatement();
            statement2 = connection.createStatement();


            String query1=String.format("SELECT SUM(milk_produced_daily) as totalmilk FROM cowmilk WHERE date='%s'",date);
            ResultSet resultSet1=statement1.executeQuery(query1);


            if(resultSet1.next())
            {
                //System.out.println("totalMilkCalculation: "+resultSet1.getInt("totalmilk"));
            }
            String query2 = String.format("INSERT INTO ledger(id,date,total_milk_remaining) values(%d,'%s',%d) ON CONFLICT(date) DO UPDATE SET total_milk_remaining=EXCLUDED.total_milk_remaining",++uniqueId3,date,resultSet1.getInt("totalmilk")-totalPurchasedMilkQuantity);
            statement2.executeUpdate(query2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int reduceTotalMilkRemaining(Connection connection, int RequiredMilkQuantity) {

        Statement statement1, statement2;

        //To Format Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);

        int totalMilkRemaining=0;
        try {
            statement1 = connection.createStatement();
            String query1=String.format("SELECT total_milk_remaining from ledger where date='%s'",date);
            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                totalMilkRemaining= resultSet1.getInt("total_milk_remaining");
                //System.out.println("totalMilkRemaining: "+totalMilkRemaining);
                if(RequiredMilkQuantity<=totalMilkRemaining)
                {
                    totalMilkRemaining=totalMilkRemaining-RequiredMilkQuantity;
                    if(totalMilkRemaining<0)
                    {
                        totalMilkRemaining=0;
                    }
                    else {
                        totalPurchasedMilkQuantity+=RequiredMilkQuantity;
                    }
                    //            System.out.println(totalPurchasedMilkQuantity);
                    statement2=connection.createStatement();
                    String query2 = String.format("UPDATE ledger SET total_milk_remaining=%d,total_purchased_milk=%d WHERE date='%s'", totalMilkRemaining,totalPurchasedMilkQuantity,date);
                    statement2.executeUpdate(query2);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        totalProfitCalculate(connection);
        return totalMilkRemaining;
    }

    public static int viewTotalProfit(Connection connection,String date,String month) {

        Statement statement1;

        //TO Format Month
        String monthString="2022/"+month+"%%";
//        System.out.println("date= "+date+"\t"+"month= "+month);

        int totalProfit=0;
        try {
            statement1=connection.createStatement();
            ResultSet resultSet1;
            if(date!=null) {
                resultSet1 = statement1.executeQuery(String.format("SELECT sum(total_profit) as sum FROM ledger WHERE date='%s'",date));
                if(resultSet1.next()) {
                    totalProfit = resultSet1.getInt("sum");
                }
            }
            else if (month!=null)
            {
                resultSet1 = statement1.executeQuery(String.format("SELECT sum(total_profit) as sum FROM ledger WHERE date LIKE '%s'", monthString));
//                System.out.println("not working");
                if(resultSet1.next()) {
                    totalProfit = resultSet1.getInt("sum");
                }
            }
            else
            {
                resultSet1 = statement1.executeQuery("SELECT sum(total_profit) as sum FROM ledger");
                if(resultSet1.next()) {
                    totalProfit = resultSet1.getInt("sum");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
//        System.out.println("total profit: "+totalProfit);
        return totalProfit;
    }

    public static void totalProfitCalculate(Connection connection) {

        Statement statement1,statement2;

        //To Format Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);

        int totalRevenueGenerated=0,totalExpenditure=0;
        try {
            statement1=connection.createStatement();
            String query1="SELECT total_expenditure from ledger";
            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                totalExpenditure=resultSet1.getInt("total_expenditure");
            }
            //System.out.println("totalPurchasedMilkQuantity: "+totalPurchasedMilkQuantity);
            totalPurchasedMilkPrice=totalPurchasedMilkQuantity*22;
            totalRevenueGenerated=totalPurchasedMilkPrice;
            statement2 = connection.createStatement();
            String query2= String.format("UPDATE ledger SET total_revenue_generated=%d,total_profit=%d WHERE date='%s'",totalRevenueGenerated,totalRevenueGenerated-totalExpenditure,date);
            statement2.executeUpdate(query2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void totalExpenditureCalculate(Connection connection) {

        Statement statement1,statement2;

        //To Format Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);

        int totalExpenditure=0;
        try {
            statement1=connection.createStatement();
            String query1="SELECT SUM(expenditure) as total_expenditure from cows";
            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                totalExpenditure=resultSet1.getInt("total_expenditure");
            }
            statement2= connection.createStatement();
            String query2=String.format("UPDATE ledger SET total_expenditure=%d WHERE date='%s'",totalExpenditure,date);
            statement2.executeUpdate(query2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setUniqueId(Connection connection) {

        Statement statement1,statement2,statement3;

        try {
            //To set uniqueId1
            statement1=connection.createStatement();
            String query1="SELECT MAX(id) AS max_id from cows";
            ResultSet resultSet1=statement1.executeQuery(query1);
            if(resultSet1.next())
            {
                uniqueId1=resultSet1.getInt("max_id");
            }
            //To set uniqueId2
            statement2= connection.createStatement();
            String query2="SELECT MAX(id) AS max_id from cowmilk";
            ResultSet resultSet2=statement2.executeQuery(query2);
            if(resultSet2.next())
            {
                uniqueId2=resultSet2.getInt("max_id");
            }
            //To set uniqueId3
            statement3= connection.createStatement();
            String query3="SELECT MAX(id) AS max_id from ledger";
            ResultSet resultSet3=statement3.executeQuery(query3);
            if(resultSet3.next())
            {
                uniqueId3=resultSet3.getInt("max_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("uniqueId1: "+uniqueId1+"\t"+"uniqueId2: "+uniqueId2+"\t"+"uniqueId3: "+uniqueId3);
    }
}


