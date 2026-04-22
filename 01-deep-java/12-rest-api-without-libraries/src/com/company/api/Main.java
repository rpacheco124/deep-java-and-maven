package com.company.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 8067;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/health", Main::handleHealth);
        server.createContext("/users", new UserHandler());

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        System.out.println("Server running on http://localhost:" + port);
        System.out.println("GET  /health");
        System.out.println("GET  /users");
        System.out.println("POST /users");
    }

    private static void handleHealth(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Allow", "GET");
            ResponseUtil.sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        ResponseUtil.sendJson(exchange, 200, "{\"status\":\"UP\"}");
    }
}
