package com.training.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection dbConnect(String dbname, String user, String pass){
        Connection conn =null;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);
            if (conn!=null){
                System.out.println("connection established");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        DatabaseOperations.setUniqueId(conn);
        return  conn;
    }
}
