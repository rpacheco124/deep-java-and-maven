package com.dachser.annotatedapi.controller;

import com.dachser.annotatedapi.annotations.Route;

public class SystemController {

    @Route(path = "/health", method = "GET")
    public String health() {
        return "{\"status\":\"UP\"}";
    }
}
