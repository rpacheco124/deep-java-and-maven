package com.dachser.annotatedapi.framework;

import java.lang.reflect.Method;

public class RouteDefinition {
    private final String httpMethod;
    private final String path;
    private final Object controller;
    private final Method method;

    public RouteDefinition(String httpMethod, String path, Object controller, Method method) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.controller = controller;
        this.method = method;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
