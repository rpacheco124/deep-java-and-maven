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

## Full Execution Flow (Startup -> Request -> Response)

### 1) Server Startup (`Main`)

When you run:

```bash
java -cp target com.company.annotatedapi.Main
```

`Main` does this in order:

1. Creates `RouteRegistry`.
2. Registers controllers:
   - `SystemController`
   - `UserController`
3. Creates `HttpServer` on port `8076`.
4. Registers one global context: `"/"` with `DispatchHandler`.
5. Starts the server thread pool.

### 2) Route Registration (Reflection Phase)

During `registry.registerController(controller)`, reflection is used:

1. `controller.getClass()` gets the runtime class.
2. `clazz.getDeclaredMethods()` reads all methods.
3. For each method, `method.getAnnotation(Route.class)` checks if `@Route` exists.
4. If present, the framework extracts:
   - `route.method()` (GET/POST/...)
   - `route.path()` (`/health`, `/users`, ...)
5. It stores a route entry in an internal map:

```text
"GET /health" -> (SystemController, health)
"GET /users"  -> (UserController, listUsers)
"POST /users" -> (UserController, createUser)
```

### 3) Request Dispatch (Runtime Phase)

For each incoming HTTP request:

1. `DispatchHandler` reads request method + path from `HttpExchange`.
2. Builds route key (example: `GET /users`).
3. Looks up that key in `RouteRegistry`.
4. If not found -> returns `404 {"error":"Route not found"}`.
5. If found -> invokes controller method with reflection:
   - `targetMethod.invoke(controller)` for no-arg methods
   - `targetMethod.invoke(controller, exchange)` when method needs `HttpExchange`
6. Takes returned JSON string and sends it via `ResponseUtil`.

---

## Visual Pipeline

```text
Client (curl/Postman/browser)
          |
          v
   HttpServer (JDK)
          |
          v
   DispatchHandler
          |
          |  key = METHOD + PATH
          v
     RouteRegistry  <--- built at startup via reflection on @Route
          |
          v
  RouteDefinition (controller instance + java.lang.reflect.Method)
          |
          v
 method.invoke(...)
          |
          v
  Controller method returns JSON String
          |
          v
 ResponseUtil -> HTTP status + JSON body
          |
          v
       Client receives response
```

### Example: `POST /users`

```text
POST /users
  -> DispatchHandler finds "POST /users"
  -> invokes UserController.createUser(HttpExchange)
  -> method reads request body, creates user
  -> sets exchange attribute http.status = 201
  -> returns JSON: {"id":1,"name":"Ana"}
  -> DispatchHandler sends HTTP 201 + JSON
```

---

## Project Structure

```text
src/com/company/annotatedapi/Main.java
src/com/company/annotatedapi/annotations/Route.java
src/com/company/annotatedapi/framework/RouteDefinition.java
src/com/company/annotatedapi/framework/RouteRegistry.java
src/com/company/annotatedapi/framework/DispatchHandler.java
src/com/company/annotatedapi/controller/SystemController.java
src/com/company/annotatedapi/controller/UserController.java
src/com/company/annotatedapi/util/JsonUtil.java
src/com/company/annotatedapi/util/ResponseUtil.java
```

---

## Commands used in this folder:

To generate java class files:
```bash
javac -d target $(find src -name "*.java")
```

To execute the REST API server:
```bash
java -cp target com.company.annotatedapi.Main
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
