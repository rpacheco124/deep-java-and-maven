# Java Reflection: Dynamic Class Loading

This is the most powerful application of reflection: **loading and executing classes that don't exist at compile time**. The class is loaded from the classpath at runtime, instantiated, and its methods are invoked — all without a single `import` statement.

This is the foundation of **plugin systems**. Applications like IDEs (IntelliJ, Eclipse), application servers (Tomcat, WildFly), and frameworks (Spring) use this technique to load extensions, drivers, and components dynamically.

---

## How Dynamic Loading Works

The process combines everything we've learned in the previous reflection modules:

1. **Load the class** from the classpath: `Class.forName("fully.qualified.ClassName")`
2. **Create an instance** without using `new`: `clazz.getDeclaredConstructor().newInstance()`
3. **Find and invoke methods** dynamically: `method.invoke(instance, args)`

#### What Makes This Different from Previous Modules

| Module | What it does | Knows the class at compile time? |
|--------|-------------|:--------------------------------:|
| 09 - Inspect | Reads class metadata | Yes (class is on classpath) |
| 10 - Invoke | Calls methods by name | Yes (class is on classpath) |
| **11 - Load** | **Loads a class from an external JAR** | **No (class is not imported)** |

In this example, `ReflectionLoader.java` has **no import** for `GreetingSout`. The class is loaded entirely at runtime from `greeting.jar` — the compiler has no knowledge of it.

---

## Real-World Applications

- **JDBC Drivers**: `Class.forName("com.mysql.cj.jdbc.Driver")` loads the database driver at runtime. Your code doesn't import any MySQL classes directly.
- **Servlet Containers**: Tomcat reads your `web.xml` or `@WebServlet` annotations and loads your servlet classes dynamically.
- **Spring Framework**: Scans packages for `@Component`, `@Service`, `@Repository` annotations and loads/instantiates those classes at startup.
- **Plugin Systems**: Applications define an interface, and plugins implement it. The application loads plugin JARs at runtime without recompilation.

---

## Commands used in this folder:

To generate java class:
```
javac -d target -cp libs/greeting.jar src/com/company/ReflectionLoader.java
```
To execute java class:
```
java -cp target;libs/greeting.jar com.company.ReflectionLoader
```

***NOTE: Notice that the compilation requires `greeting.jar` on the classpath (`-cp libs/greeting.jar`). This is because `Class.forName()` looks for the class on the classpath at runtime. If you remove the JAR from the classpath, you'll get a `ClassNotFoundException` — the most common error when working with dynamic class loading.***
