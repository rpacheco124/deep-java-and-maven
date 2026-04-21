# REST API with Annotations (Mini Framework)

This module is the "after" version of module 12.

In module 12, routes were hardcoded in `Main` with `server.createContext(...)`. In this module, routes are declared with `@Route` annotations and discovered via reflection at startup.

---

## Goal

Build the same kind of REST API, but using a tiny annotation-based dispatcher, similar to how frameworks like Spring MVC map routes.

---

## How It Works

1. Controller methods are annotated with `@Route(path, method)`.
2. `RouteRegistry` scans controllers with reflection and builds a route table.
3. `DispatchHandler` receives every HTTP request, finds the matching route, and invokes the controller method dynamically.

---

## Project Structure

```text
src/com/dachser/annotatedapi/Main.java
src/com/dachser/annotatedapi/annotations/Route.java
src/com/dachser/annotatedapi/framework/RouteDefinition.java
src/com/dachser/annotatedapi/framework/RouteRegistry.java
src/com/dachser/annotatedapi/framework/DispatchHandler.java
src/com/dachser/annotatedapi/controller/SystemController.java
src/com/dachser/annotatedapi/controller/UserController.java
src/com/dachser/annotatedapi/util/JsonUtil.java
src/com/dachser/annotatedapi/util/ResponseUtil.java
```

---

## Commands used in this folder:

To generate java class files:
```bash
javac -d target $(find src -name "*.java")
```

To execute the REST API server:
```bash
java -cp target com.dachser.annotatedapi.Main
```

The server starts on:
```text
http://localhost:8076
```

---

## Testing the API

Check health:
```bash
curl -i http://localhost:8076/health
```

List users (initially empty):
```bash
curl -i http://localhost:8076/users
```

Create a user:
```bash
curl -i -X POST http://localhost:8076/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ana"}'
```

List users again:
```bash
curl -i http://localhost:8076/users
```

Try an unknown route:
```bash
curl -i http://localhost:8076/unknown
```

---

## Before vs After

- **Module 12:** route mapping is manual (`server.createContext` for each endpoint).
- **Module 14:** route mapping is declarative (`@Route`) and resolved with reflection.

Both are "no external libraries", but module 14 shows the first step toward building your own framework.

---

***NOTE: This is still a learning implementation: in-memory storage, manual JSON parsing, and minimal error handling.***
