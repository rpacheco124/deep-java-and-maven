package com.company.annotatedapi.controller;

import com.company.annotatedapi.annotations.Route;
import com.company.annotatedapi.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController {

    private static final List<User> USERS = new ArrayList<>();
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);

    @Route(path = "/users", method = "GET")
    public String listUsers() {
        StringBuilder json = new StringBuilder("[");

        synchronized (USERS) {
            for (int i = 0; i < USERS.size(); i++) {
                if (i > 0) {
                    json.append(',');
                }
                json.append(USERS.get(i).toJson());
            }
        }

        json.append(']');
        return json.toString();
    }

    @Route(path = "/users", method = "POST")
    public String createUser(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String name = JsonUtil.extractStringField(body, "name");

        if (name == null || name.isBlank()) {
            exchange.setAttribute("http.status", 400);
            return "{\"error\":\"Invalid JSON. Expected field: name\"}";
        }

        User user = new User(NEXT_ID.getAndIncrement(), name.trim());
        synchronized (USERS) {
            USERS.add(user);
        }

        exchange.setAttribute("http.status", 201);
        return user.toJson();
    }

    // @Route(path = "/users", method = "DELETE")
    // public String deleteUser(HttpExchange exchange) {
    // exchange.setAttribute("http.status", 501);
    // return "{\"error\":\"Not implemented\"}";
    // }

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
