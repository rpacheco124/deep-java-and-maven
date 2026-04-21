package com.dachser.routes;

import com.dachser.annotations.Route;

import java.lang.reflect.Method;

public class RouteScanner {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = RouteExample.class;
        Object instance = clazz.getDeclaredConstructor().newInstance();

        System.out.println("=== Scanning " + clazz.getSimpleName() + " for @Route ===\n");

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Route.class)) {
                Route route = method.getAnnotation(Route.class);

                System.out.println("Found route: " + route.method() + " " + route.path());
                System.out.println("  -> Method: " + method.getName() + "()");
                System.out.println("  -> Response: " + method.invoke(instance));
                System.out.println();
            }
        }
    }
}
