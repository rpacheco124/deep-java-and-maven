package com.company.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserHandler implements HttpHandler {

    private static final List<User> USERS = new ArrayList<>();
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (!"/users".equals(path)) {
            ResponseUtil.sendJson(exchange, 404, "{\"error\":\"Not found\"}");
            return;
        }

        String method = exchange.getRequestMethod();

        if ("GET".equals(method)) {
            handleGetUsers(exchange);
            return;
        }

        if ("POST".equals(method)) {
            handleCreateUser(exchange);
            return;
        }

        exchange.getResponseHeaders().add("Allow", "GET, POST");
        ResponseUtil.sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
    }

    private void handleGetUsers(HttpExchange exchange) throws IOException {
        StringBuilder json = new StringBuilder("[");

        synchronized (USERS) {
            for (int i = 0; i < USERS.size(); i++) {
                User user = USERS.get(i);
                if (i > 0) {
                    json.append(',');
                }
                json.append(user.toJson());
            }
        }

        json.append(']');
        ResponseUtil.sendJson(exchange, 200, json.toString());
    }

    private void handleCreateUser(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String name = JsonUtil.extractStringField(body, "name");

        if (name == null || name.isBlank()) {
            ResponseUtil.sendJson(exchange, 400, "{\"error\":\"Invalid JSON. Expected field: name\"}");
            return;
        }

        User user = new User(NEXT_ID.getAndIncrement(), name.trim());
        synchronized (USERS) {
            USERS.add(user);
        }

        ResponseUtil.sendJson(exchange, 201, user.toJson());
    }

    private static class User {
        private final int id;
        private final String name;

        private User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private String toJson() {
            return "{\"id\":" + id + ",\"name\":\"" + JsonUtil.escape(name) + "\"}";
        }
    }
}
