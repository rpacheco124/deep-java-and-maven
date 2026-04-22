package com.company.annotatedapi.framework;

import com.company.annotatedapi.annotations.Route;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class RouteRegistry {

    private final Map<String, RouteDefinition> routes = new LinkedHashMap<>();

    public void registerController(Object controller) {
        Class<?> clazz = controller.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            Route route = method.getAnnotation(Route.class);
            if (route == null) {
                continue;
            }

            String httpMethod = route.method().toUpperCase();
            String path = normalizePath(route.path());
            String key = buildKey(httpMethod, path);

            if (routes.containsKey(key)) {
                throw new IllegalStateException("Duplicate route: " + key);
            }

            method.setAccessible(true);
            routes.put(key, new RouteDefinition(httpMethod, path, controller, method));
        }
    }

    public RouteDefinition find(String httpMethod, String path) {
        return routes.get(buildKey(httpMethod.toUpperCase(), normalizePath(path)));
    }

    public Map<String, RouteDefinition> getRoutes() {
        return routes;
    }

    private String buildKey(String httpMethod, String path) {
        return httpMethod + " " + path;
    }

    private String normalizePath(String path) {
        if (path == null || path.isEmpty()) {
            return "/";
        }
        if (!path.startsWith("/")) {
            return "/" + path;
        }
        return path;
    }
}
