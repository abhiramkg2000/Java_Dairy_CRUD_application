package com.training.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiHandlerTest {

    @Test
    void getCowsRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/cows"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void getCowRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/cow/101"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void postCowRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().
        uri(URI.create("http://localhost:8000/api/cow"))
                .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"age\":4,\n" +
                        "    \"color\":\"orange\",\n" +
                        "    \"gender\":\"female\",\n" +
                        "    \"milking\":\"true\",\n" +
                        "    \"govId\":101,\n" +
                        "    \"expenditure\":200\n" +
                        "}"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void putCowRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8000/api/cow/102"))
                .PUT(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"age\":7,\n" +
                        "    \"color\":\"orange\",\n" +
                        "    \"gender\":\"female\",\n" +
                        "    \"milking\":\"true\",\n" +
                        "    \"expenditure\":100\n" +
                        "}"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201,response.statusCode());
    }

    @Test
    void deleteCowRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8000/api/cow/103"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void getCowMilkRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/cowmilk/101"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void postCowMilkRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8000/api/cowmilk/101?date=2022/10/04"))
                .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"milkProducedDaily\":30\n" +
                        "}"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void putCowMilkRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8000/api/cowmilk/101?date=2022/10/04"))
                .PUT(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"milkProducedDaily\":100\n" +
                        "}"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201,response.statusCode());
    }

    @Test
    void getMilkRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/milk?date=2022/10/03"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void getMilkPurchaseRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/milkpurchase?date=2022/10/03"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void postMilkPurchaseRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8000/api/milkpurchase"))
                .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"milkQuantity\":20\n" +
                        "}"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }

    @Test
    void getProfitRoute() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/api/profit"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
    }
}