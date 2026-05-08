package com.company.annotatedapi.framework;

import com.company.annotatedapi.util.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatchHandler implements HttpHandler {

    private final RouteRegistry registry;

    public DispatchHandler(RouteRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            RouteDefinition route = registry.find(method, path);
            if (route == null) {
                ResponseUtil.sendJson(exchange, 404, "{\"error\":\"Route not found\"}");
                return;
            }

            checkAuth(exchange);

            Method targetMethod = route.getMethod();
            Object controller = route.getController();
            exchange.setAttribute("http.status", 200);

            Object result;
            if (targetMethod.getParameterCount() == 1
                    && targetMethod.getParameterTypes()[0].equals(HttpExchange.class)) {
                result = targetMethod.invoke(controller, exchange);
            } else if (targetMethod.getParameterCount() == 0) {
                result = targetMethod.invoke(controller);
            } else {
                ResponseUtil.sendJson(exchange, 500, "{\"error\":\"Invalid handler signature\"}");
                return;
            }

            int status = readStatusCode(exchange);
            if (result == null) {
                ResponseUtil.sendJson(exchange, status, "{}");
                return;
            }

            ResponseUtil.sendJson(exchange, status, result.toString());
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getTargetException();
            String message = cause != null ? cause.getMessage() : ex.getMessage();
            ResponseUtil.sendJson(exchange, 500, "{\"error\":\"" + escape(message) + "\"}");
        } catch (Exception ex) {
            ResponseUtil.sendJson(exchange, 500, "{\"error\":\"" + escape(ex.getMessage()) + "\"}");
        }
    }

    private void checkAuth(HttpExchange exchange) {
        String authorized = exchange.getRequestHeaders().getFirst("Authorized");
        // Add authentication logic here
    }

    private String escape(String value) {
        if (value == null) {
            return "Unexpected server error";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private int readStatusCode(HttpExchange exchange) {
        Object value = exchange.getAttribute("http.status");
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return 200;
    }
}
