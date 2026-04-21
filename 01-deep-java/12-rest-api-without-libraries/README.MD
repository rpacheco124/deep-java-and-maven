# REST API Without Libraries

In this section we create a small REST API using only the JDK, without Spring, Jersey, or any external dependency.

We use `com.sun.net.httpserver.HttpServer`, which is built into Java (`jdk.httpserver` module), to expose HTTP endpoints and return JSON responses.

---

## What You Will Build

A simple in-memory API with these endpoints:

- `GET /health` -> returns server status
- `GET /users` -> returns all users
- `POST /users` -> creates a new user

Example payload for `POST /users`:

```json
{"name":"Ana"}
```

---

## Why This Matters

Frameworks hide many HTTP details. By doing it manually, you understand the core mechanics:

- How a server binds to a port
- How handlers map to routes
- How to read request method, URI, and body
- How to send status codes and JSON responses
- How to structure API code without frameworks

---

## Project Structure

```text
src/com/dachser/api/Main.java
src/com/dachser/api/UserHandler.java
src/com/dachser/api/ResponseUtil.java
src/com/dachser/api/JsonUtil.java
```

---

## Commands used in this folder:

To generate java class files:
```bash
javac -d target $(find src -name "*.java")
```

To execute the REST API server:
```bash
java -cp target com.dachser.api.Main
```

The server starts on:
```text
http://localhost:8067
```

---

## Testing the API

Check health:
```bash
curl -i http://localhost:8067/health
```

List users (initially empty):
```bash
curl -i http://localhost:8067/users
```

Create a user:
```bash
curl -i -X POST http://localhost:8067/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ana"}'
```

List users again:
```bash
curl -i http://localhost:8067/users
```

Try an invalid payload:
```bash
curl -i -X POST http://localhost:8067/users -d '{}'
```

---

## Expected Behavior

- `GET /health` -> `200` and `{"status":"UP"}`
- `GET /users` -> `200` and `[]` or list of users
- `POST /users` with valid `name` -> `201` and created user JSON
- `POST /users` with invalid JSON/body -> `400`
- Unsupported methods (e.g. `DELETE /users`) -> `405`

---

***NOTE: This implementation is intentionally minimal for learning purposes. It stores data in memory, parses JSON manually, and has no persistence, validation framework, authentication, or production-grade error handling.***
