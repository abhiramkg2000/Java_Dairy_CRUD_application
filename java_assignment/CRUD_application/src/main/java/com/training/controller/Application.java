package com.training.controller;

import com.sun.net.httpserver.HttpServer;
import com.training.database.DatabaseConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;

class Application {
    public static void main(String[] args) throws IOException {

        ApiHandler api=new ApiHandler();
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");

        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        //Creating /api/hello route
        /*---------------------------------------------------------------------------------------*/
        //Creating /api/cows route
        server.createContext("/api/cows", (exchange->api.cowsRoute(exchange,con)));
        /*---------------------------------------------------------------------------------------*/
        //Creating /api/cow route
        server.createContext("/api/cow", (exchange->api.cowRoute(exchange,con)));
        /*---------------------------------------------------------------------------------------*/
        //Creating /api/cowmilk route
        server.createContext("/api/cowmilk",(exchange->api.cowMilkRoute(exchange,con)));
        /*---------------------------------------------------------------------------------------*/
        //Creating /api/milk route
        server.createContext("/api/milk", (exchange->api.milkRoute(exchange,con)));
        /*---------------------------------------------------------------------------------------*/
        //Creating /api/milkpurchase route
        server.createContext("/api/milkpurchase", (exchange->api.milkPurchaseRoute(exchange,con)));
        /*---------------------------------------------------------------------------------------*/
        //Creating /api/profit route
        server.createContext("/api/profit", (exchange->api.profitRoute(exchange,con)));
        /*---------------------------------------------------------------------------------------*/
        server.setExecutor(null); // creates a default executor
        server.start();
    }
    //Function to parse query parameter
    /*---------------------------------------------------------------------------------------*/
}
