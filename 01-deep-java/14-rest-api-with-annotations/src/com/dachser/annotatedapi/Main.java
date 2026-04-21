package com.dachser.annotatedapi;

import com.dachser.annotatedapi.controller.SystemController;
import com.dachser.annotatedapi.controller.UserController;
import com.dachser.annotatedapi.framework.DispatchHandler;
import com.dachser.annotatedapi.framework.RouteDefinition;
import com.dachser.annotatedapi.framework.RouteRegistry;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8076;

        /*
         * We can also make this part dynamic using reflection
         * and an annotation like @Controller of target CLASS,
         * but to avoid making the course too complicated we will do it hardcoded
         */
        RouteRegistry registry = new RouteRegistry();
        registry.registerController(new SystemController());
        registry.registerController(new UserController());

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new DispatchHandler(registry));
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        System.out.println("Server running on http://localhost:" + port);
        System.out.println("Registered routes:");
        for (Map.Entry<String, RouteDefinition> entry : registry.getRoutes().entrySet()) {
            System.out.println("- " + entry.getKey());
        }
    }
}
