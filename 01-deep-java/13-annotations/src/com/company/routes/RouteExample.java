package com.company.routes;

import com.company.annotations.Route;

public class RouteExample {

    @Route(path = "/hello", method = "GET")
    public String hello() {
        return "Hello, World!";
    }

    @Route(path = "/users", method = "POST")
    public String createUser() {
        return "User created";
    }

    public String internalMethod() {
        return "This is not a route";
    }
}
