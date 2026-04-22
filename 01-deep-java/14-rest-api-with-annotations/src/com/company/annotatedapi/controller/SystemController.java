package com.company.annotatedapi.controller;

import com.company.annotatedapi.annotations.Route;

public class SystemController {

    @Route(path = "/health", method = "GET")
    public String health() {
        return "{\"status\":\"UP\"}";
    }
}
