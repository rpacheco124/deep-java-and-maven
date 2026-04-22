# Java Annotations

Annotations are metadata that you attach to code elements (classes, methods, fields, parameters). They don't change what the code does directly, but they provide information that can be read at **compile time** or **runtime** by tools, frameworks, and your own code.

If you've ever used `@Override`, `@Deprecated`, `@Test`, `@Autowired`, or `@Entity`, you've used annotations. This module teaches you how they work under the hood and how to create your own.

---

## Built-in Annotations

Java provides several annotations out of the box:

| Annotation             | Purpose                                                                                    |
| ---------------------- | ------------------------------------------------------------------------------------------ |
| `@Override`            | Tells the compiler this method overrides a superclass method. Compile error if it doesn't. |
| `@Deprecated`          | Marks an element as deprecated. The compiler generates a warning when it's used.           |
| `@SuppressWarnings`    | Tells the compiler to suppress specific warnings.                                          |
| `@FunctionalInterface` | Ensures an interface has exactly one abstract method (for lambdas).                        |

---

## Creating Custom Annotations

An annotation is defined using `@interface`:

```java
public @interface MyAnnotation {
    String value();
}
```

### Meta-Annotations

Meta-annotations are annotations that you put on your annotation definition to control its behavior:

| Meta-Annotation | Purpose                           | Common Values                                |
| --------------- | --------------------------------- | -------------------------------------------- |
| `@Retention`    | When is the annotation available? | `SOURCE`, `CLASS`, `RUNTIME`                 |
| `@Target`       | Where can it be applied?          | `TYPE`, `METHOD`, `FIELD`, `PARAMETER`, etc. |
| `@Documented`   | Include in Javadoc?               | (no values)                                  |
| `@Inherited`    | Do subclasses inherit it?         | (no values)                                  |

### `@Retention` - The Most Important Meta-Annotation

| Retention Policy | Available at compile time? | Available in .class? | Available at runtime? | Use Case                                                                       |
| ---------------- | :------------------------: | :------------------: | :-------------------: | ------------------------------------------------------------------------------ |
| `SOURCE`         |            Yes             |          No          |          No           | Compiler checks only (`@Override`, `@SuppressWarnings`)                        |
| `CLASS`          |            Yes             |         Yes          |          No           | Default. Bytecode analysis tools.                                              |
| `RUNTIME`        |            Yes             |         Yes          |          Yes          | **Reflection**. This is what frameworks use (`@Autowired`, `@Entity`, `@Test`) |

---

## Practical Example 1: `@Route`

This example shows how a framework can discover route methods with reflection.

### Step 1: Define the Annotation

**Route.java** - marks a method as an HTTP endpoint:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Route {
    String path();
    String method() default "GET";
}
```

### Step 2: Use the Annotation

**RouteExample.java**:
```java
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
```

### Step 3: Scan and Execute with Reflection

**RouteScanner.java** - reads `@Route` at runtime:
```java
public class RouteScanner {
    public static void main(String[] args) throws Exception {
        // Scans RouteExample class for @Route annotations
    }
}
```

### Commands for This Example

```bash
javac -d target $(find src -name "*.java")
java -cp target com.company.routes.RouteScanner
```

This is exactly the pattern that Spring uses with `@RequestMapping` and `@GetMapping`.

---

## Practical Example 2: `@LogResult`

This example shows how a framework can find methods that should be logged.

### Step 1: Define the Annotation

**LogResult.java** - marks a method whose result should be logged:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogResult {
}
```

### Step 2: Use the Annotation

**LogResultExample.java**:
```java
public class LogResultExample {

    @LogResult
    public String auditLogin() {
        return "Login audited";
    }

    @LogResult
    public String auditPayment() {
        return "Payment audited";
    }

    public String internalMethod() {
        return "This is not logged";
    }
}
```

### Step 3: Scan and Execute with Reflection

**LogResultScanner.java** - reads `@LogResult` at runtime:
```java
public class LogResultScanner {
    public static void main(String[] args) throws Exception {
        // Scans LogResultExample class for @LogResult annotations
    }
}
```

### Commands for This Example

```bash
javac -d target $(find src -name "*.java")
java -cp target com.company.logs.LogResultScanner
```

This is the same idea used by audit and tracing frameworks.


---

## The Connection: Annotations + Reflection = Frameworks

| Framework         | Annotation             | What Reflection Does                                                                            |
| ----------------- | ---------------------- | ----------------------------------------------------------------------------------------------- |
| **Spring**        | `@Autowired`           | Scans fields, finds matching beans, injects them via `field.set()`                              |
| **Spring MVC**    | `@GetMapping("/path")` | Scans methods, reads path value, registers as HTTP handler                                      |
| **JPA/Hibernate** | `@Entity`, `@Column`   | Scans class fields, maps them to database table columns                                         |
| **JUnit**         | `@Test`                | Scans methods, invokes each one as a test case                                                  |
| **Jackson**       | `@JsonProperty`        | Reads field annotations to know how to serialize/deserialize JSON                               |
| **Lombok**        | `@Getter`, `@Setter`   | Uses internal compiler APIs at **compile time** (`SOURCE` retention) to modify the AST directly |

when you understand both, you understand how every major Java framework operates internally.

---

## Commands Used in This Section

Compile the examples:
```bash
javac -d target $(find src -name "*.java")
```

Run each example:
```bash
java -cp target com.company.routes.RouteScanner
java -cp target com.company.logs.LogResultScanner
```

***NOTE: `@Retention(RetentionPolicy.RUNTIME)` is the critical piece in this module — without it, annotations are discarded after compilation and reflection cannot see them. This is the most common mistake when creating custom annotations for runtime frameworks.***
