# Java Reflection: Invoking Methods Dynamically

In the previous section we learned how to **inspect** classes at runtime. Now we take the next step: **executing methods dynamically** without knowing them at compile time.

This is a fundamental concept behind how frameworks work. When Spring calls your `@RequestMapping` method, or when JUnit runs your `@Test` methods, they don't know your method names at compile time — they discover and invoke them at runtime using reflection.

---

## How Method Invocation Works

The process follows three steps:

1. **Get the `Class` object**: Using `Class.forName()` or `.getClass()`
2. **Get the `Method` object**: Using `clazz.getMethod("methodName", parameterTypes...)`
3. **Invoke the method**: Using `method.invoke(instance, arguments...)`

#### Key Classes Used

| Class | Purpose |
|-------|---------|
| `Class<?>` | Represents the class itself |
| `Method` | Represents a single method, obtained via `getMethod()` or `getDeclaredMethod()` |
| `method.invoke(obj, args)` | Executes the method on the given object with the given arguments |

#### `getMethod()` vs `getDeclaredMethod()`

| Method | Finds | Includes private? |
|--------|-------|:-----------------:|
| `getMethod()` | Public methods (including inherited) | No |
| `getDeclaredMethod()` | All methods declared in the class (not inherited) | Yes (with `setAccessible(true)`) |

---

## Commands used in this folder:

To generate java class:
```
javac ReflectionInvoker.java
```
To execute java class:
```
java ReflectionInvoker
```

***NOTE: This is exactly how testing frameworks like JUnit work internally. JUnit scans your test class for methods annotated with `@Test`, then uses `method.invoke()` to execute each one. The framework never knows your test method names at compile time.***
