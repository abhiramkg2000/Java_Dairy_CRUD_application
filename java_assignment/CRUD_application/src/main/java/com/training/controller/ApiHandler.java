package com.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.training.database.DatabaseOperations;
import com.training.model.*;
import com.training.utils.SplitDate;
import com.training.utils.SplitMonth;
import com.training.utils.SplitPath;
import com.training.validate.Validation;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ApiHandler {

    public void cowsRoute(HttpExchange exchange,Connection con) throws IOException {

        //To allow CORS request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        //To set response content-type as JSON
        exchange.getResponseHeaders().add("Content-Type","application/json");

        if ("GET".equals(exchange.getRequestMethod())) {
            ObjectMapper objectMapper = new ObjectMapper();

            //To view data in database
            List<Cow> cowList = DatabaseOperations.viewCows(con);

            //Response Text
            String []cowAsString={"",""};
            cowAsString[0] ="{\"data\":"+objectMapper.writeValueAsString(cowList);
            cowAsString[1]=",\"No_of_cows\":"+cowList.size()+"}";
            String respText=cowAsString[0]+cowAsString[1];

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void cowRoute(HttpExchange exchange, Connection con) throws IOException{

        //To allow CORS request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        //To set response content-type as JSON
        exchange.getResponseHeaders().add("Content-Type","application/json");

        //To make successfull preflight request
        if("OPTIONS".equals(exchange.getRequestMethod()))
        {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
            exchange.sendResponseHeaders(200,1);
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            //To get path parameter
            String param = SplitPath.splitPath(exchange.getRequestURI().getRawPath());
            int govId=Integer.parseInt(param);
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();

            //To send Message as JSON
            Message message=new Message();

            //To view data in database
            List<Cow> cowList=DatabaseOperations.viewCow(con,govId);

            //Response Text
            String respText;
            if(cowList.isEmpty())
            {
                message.setMessage("there is no cow data,check the id");

                //To set Response Text
                respText = objectMapper.writeValueAsString(message);
            }
            else
            {
                //To set Response Text
                respText = "{\"data\":"+objectMapper.writeValueAsString(cowList.get(0))+"}";
            }

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else if ("POST".equals(exchange.getRequestMethod())) {
            //TO convert the InputStream to a String
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();
            boolean check= Validation.checkInsertCow(con,objectMapper.readValue(result, Cow.class));

            //To send Message as JSON
            Message message=new Message();
            if(!check)
            {
                message.setMessage("cow data already added,try PUT method");
            }

            //Response Text
            String respText=check?result:objectMapper.writeValueAsString(message);

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else if ("PUT".equals(exchange.getRequestMethod())) {
            //To get path parameter
            String param = SplitPath.splitPath(exchange.getRequestURI().getRawPath());
            int govId=Integer.parseInt(param);
            /*---------------------------------------------------------------------------------------*/
            //TO convert the InputStream to a String
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();
            boolean check=Validation.checkUpdateCow(con,objectMapper.readValue(result, Cow.class),govId);

            //To send Message as JSON
            Message message=new Message();
            if(check)
            {
                message.setMessage("cow data updated");
            }
            else
            {
                message.setMessage("cow data not present to update");
            }

            //Response Text
            String respText=objectMapper.writeValueAsString(message);

            //Response headers and body
            exchange.sendResponseHeaders(201, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else if ("DELETE".equals(exchange.getRequestMethod())) {
            //To get path parameter
            String param = SplitPath.splitPath(exchange.getRequestURI().getRawPath());
            int govId=Integer.parseInt(param);
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();
            boolean check= Validation.checkDeleteCow(con,govId);

            //To send Message as JSON
            Message message=new Message();
            message.setMessage(check?"cow deleted":"cow data is not present to delete,check the id");

            //Response Text
            String respText=objectMapper.writeValueAsString(message);

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void cowMilkRoute(HttpExchange exchange,Connection con)throws IOException{

        //To allow CORS request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        //To set response content-type as JSON
        exchange.getResponseHeaders().add("Content-Type","application/json");

        if("GET".equals(exchange.getRequestMethod())) {
            //To get path parameter
            String param1 = SplitPath.splitPath(exchange.getRequestURI().getRawPath());
            int govId=Integer.parseInt(param1);
            /*---------------------------------------------------------------------------------------*/
            //To get date parameter
            String param2 = SplitDate.splitDate(exchange.getRequestURI().getRawQuery());
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper=new ObjectMapper();

            //To send Message as JSON
            Message message = new Message();

            //Response Text
            String respText;

            //To check whether date is in YYYY/MM/DD Format
            boolean dateFormatCheck=Validation.checkDateFormat(param2);

            if(dateFormatCheck||param2.equals("0"))
            {
                int milkProducedDaily = DatabaseOperations.readCowMilkDate(con, param2, govId);

                //To set Response Text
                respText="{\"milkproduced\":"+milkProducedDaily+"}";
            }
            else {
                message.setMessage("date should be in YYYY/MM/DD Format");

                //To set Response Text
                respText=objectMapper.writeValueAsString(message);
            }

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else if("POST".equals(exchange.getRequestMethod())) {
            //To get path parameter
            String param = SplitPath.splitPath(exchange.getRequestURI().getRawPath());
            int govId=Integer.parseInt(param);
            /*---------------------------------------------------------------------------------------*/
            //To get date parameter
            String param2 = SplitDate.splitDate(exchange.getRequestURI().getRawQuery());
            /*---------------------------------------------------------------------------------------*/
            //TO convert the InputStream to a String
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();

            //To send Message as JSON
            Message message = new Message();

            //Response Text
            String respText;

            //To check whether date is in YYYY/MM/DD Format
            boolean dateFormatCheck=Validation.checkDateFormat(param2);

            if(dateFormatCheck)
            {
                boolean check = Validation.checkInsertCowMilk(con, objectMapper.readValue(result, CowMilk.class), govId, param2);

                if (!check) {
                    message.setMessage("milk data already added,try PUT method");
                }

                //To set Response Text
                respText=check?result:objectMapper.writeValueAsString(message);
            }
            else {
                message.setMessage("date should be in YYYY/MM/DD Format");

                //To set Response Text
                respText=objectMapper.writeValueAsString(message);
            }

            //Response headers and body
            exchange.sendResponseHeaders(200,respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else if ("PUT".equals(exchange.getRequestMethod())) {
            //To get path parameter
            String param = SplitPath.splitPath(exchange.getRequestURI().getRawPath());
            int govId=Integer.parseInt(param);
            /*---------------------------------------------------------------------------------------*/
            //To get date parameter
            String param2 = SplitDate.splitDate(exchange.getRequestURI().getRawQuery());
            /*---------------------------------------------------------------------------------------*/
            //TO convert the InputStream to a String
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();

            //To send Message as JSON
            Message message = new Message();

            //To check whether date is in YYYY/MM/DD Format
            boolean dateFormatCheck=Validation.checkDateFormat(param2);

            if(dateFormatCheck)
            {
                boolean check = Validation.checkUpdateCowMilk(con, objectMapper.readValue(result, CowMilk.class), govId, param2);
                if (check) {
                    message.setMessage("milk data updated");
                } else {
                    message.setMessage("milk data not present to update");
                }
            }
            else {
                message.setMessage("date should be in YYYY/MM/DD Format");
            }

            //Response Text
            String respText=objectMapper.writeValueAsString(message);

            //Response headers and body
            exchange.sendResponseHeaders(201, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void milkRoute(HttpExchange exchange,Connection con)throws IOException{

        //To allow CORS request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        //To set response content-type as JSON
        exchange.getResponseHeaders().add("Content-Type","application/json");

        if ("GET".equals(exchange.getRequestMethod())) {
            //To get date parameter
            String param2 = SplitDate.splitDate(exchange.getRequestURI().getRawQuery());
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();

            //To send Message as JSON
            Message message=new Message();

            //Response Text
            String respText;

            //To check whether date is in YYYY/MM/DD Format
            boolean dateFormatCheck=Validation.checkDateFormat(param2);

            if(dateFormatCheck)
            {
                //To check DatabaseOperation
                int total_milk_in_a_date = DatabaseOperations.viewTotalCowMilkDate(con, param2);
                if (total_milk_in_a_date==0) {
                    message.setMessage("no milk produced in that date,check the date");

                    //To set Response Text
                    respText = objectMapper.writeValueAsString(message);
                } else {

                    //To set Response Text
                    respText = "{\"total_milk_produced_in_that_date\":"+objectMapper.writeValueAsString(total_milk_in_a_date)+"}";
                }
            }
            else {
                message.setMessage("date should be in YYYY/MM/DD Format");

                //To set Response Text
                respText=objectMapper.writeValueAsString(message);
            }

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void milkPurchaseRoute(HttpExchange exchange,Connection con)throws IOException{

        //To allow CORS request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        //To set response content-type as JSON
        exchange.getResponseHeaders().add("Content-Type","application/json");

        if ("GET".equals(exchange.getRequestMethod())) {
            //To get date parameter
            String param2 = SplitDate.splitDate(exchange.getRequestURI().getRawQuery());
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();

            //To send Message as JSON
            Message message=new Message();

            //Response Text
            String respText;

            //To check whether date is in YYYY/MM/DD Format
            boolean dateFormatCheck=Validation.checkDateFormat(param2);

            if(dateFormatCheck)
            {
                //To get total milk
                int totalMilk = DatabaseOperations.viewTotalMilk(con, param2);
//                if (totalMilk > 0) {
//
//                    //To set Response Text
//                    respText="{\"Total Milk Remaining\":"+totalMilk+"}";
//                } else {
//                    message.setMessage("No Milk Left,");
//
//                    //To set Response Text
//                    respText= objectMapper.writeValueAsString(message);
//                }
                //To set Response Text
                respText="{\"Total Milk Remaining\":"+totalMilk+"}";
            }
            else
            {
                message.setMessage("date should be in YYYY/MM/DD Format");

                //To set Response Text
                respText= objectMapper.writeValueAsString(message);
            }

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else if("POST".equals(exchange.getRequestMethod()))
        {
            //TO convert the InputStream to a String
            Scanner s = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();
            int RequiredMilkQuantity=objectMapper.readValue(result,Milk.class).getMilkQuantity();

            //To reduce total milk after purchase
            int totalMilkRemaining=DatabaseOperations.reduceTotalMilkRemaining(con,RequiredMilkQuantity);

            //To send Message as JSON
            Message message=new Message();

            //Response Text
            String respText;
            if(totalMilkRemaining<0)
            {
                message.setMessage("No Milk Left to buy");

                //To set Response Text
                respText= objectMapper.writeValueAsString(message);
            }
            else
            {
                //To set Response Text
                respText="{\"Total Milk Remaining\":"+totalMilkRemaining+",\"Purchased Milk Quantity\":"+DatabaseOperations.getTotalPurchasedMilkQuantity()+",\"Price of Purchased Milk\":"+DatabaseOperations.getTotalPurchasedMilkPrice()+"}";
            }

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    public void profitRoute(HttpExchange exchange,Connection connection)throws IOException{

        //To allow CORS request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        //To set response content-type as JSON
        exchange.getResponseHeaders().add("Content-Type","application/json");

        if ("GET".equals(exchange.getRequestMethod()))
        {
            //To get query parameters
            Map<String,String> map= SplitMonth.splitMonth(exchange.getRequestURI().getRawQuery());
            /*---------------------------------------------------------------------------------------*/
            ObjectMapper objectMapper = new ObjectMapper();

            //To send Message as JSON
            Message message = new Message();

            //Response Text
            String respText;

            //To check whether date is in YYYY/MM/DD Format
            boolean dateFormatCheck=true;

            //To check date format only if date is given in query parameter
            if(map.containsValue("date"))
            {
                dateFormatCheck = Validation.checkDateFormat(map.get("date"));
            }

            //This function gets called if either date is null or month is null or both is null
            if(dateFormatCheck||map.get("date")==null||map.get("month")==null)
            {
                //To set Response Text
                respText="{\"Total Profit Generated\":" + DatabaseOperations.viewTotalProfit(connection, map.get("date"),map.get("month"))+"}";
            }
            else
            {
                message.setMessage("date should be in YYYY/MM/DD Format");

                //To set Response Text
                respText =objectMapper.writeValueAsString(message);
            }

            //Response headers and body
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
