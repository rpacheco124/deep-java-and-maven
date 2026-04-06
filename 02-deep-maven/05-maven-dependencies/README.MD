## Maven Dependencies

Dependencies are external libraries or modules that your project needs to compile, test, or run. Maven manages these dependencies automatically, downloading them from repositories and adding them to the classpath.

---

### How Dependencies Work

When you declare a dependency in `pom.xml`, Maven:
1. Looks for it in the **local repository** (`~/.m2/repository`).
2. If not found, downloads it from the **remote repository** (Maven Central by default).
3. Stores it locally for future use.
4. Adds it to the project classpath based on its **scope**.

### Declaring Dependencies

Dependencies are declared inside the `<dependencies>` section of the `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
```

Each dependency is identified by three coordinates known as **GAV**:
- **G**roupId: The organization or group (e.g., `com.google.code.gson`)
- **A**rtifactId: The library name (e.g., `gson`)
- **V**ersion: The specific version (e.g., `2.10.1`)

---

### Transitive Dependencies

One of Maven's most powerful features is **transitive dependency resolution**. When you add a dependency, Maven also downloads all the dependencies that library needs (its own dependencies).

For example, if you add `spring-boot-starter-web`, Maven will also download:
- `spring-web`
- `spring-webmvc`
- `tomcat-embed-core`
- And many others...

You can inspect the full dependency tree with:

```
mvn dependency:tree
```

This command is essential for understanding what libraries are actually included in your project.

---

### Dependency Scopes

Scopes control **when** and **where** a dependency is available. Maven provides 6 scopes:

| Scope        | Compile Classpath | Test Classpath | Runtime Classpath | Packaged in JAR/WAR | Use Case |
|--------------|:-----------------:|:--------------:|:-----------------:|:--------------------:|----------|
| **compile**  | Yes | Yes | Yes | Yes | Default scope. Libraries needed at all stages (e.g., `gson`, `spring-core`) |
| **provided** | Yes | Yes | No  | No  | Available at compile time but provided by the runtime environment (e.g., `servlet-api`, `lombok`) |
| **runtime**  | No  | Yes | Yes | Yes | Not needed for compilation but required at runtime (e.g., JDBC drivers like `mysql-connector-java`) |
| **test**     | No  | Yes | No  | No  | Only available during test compilation and execution (e.g., `junit`, `mockito`) |
| **system**   | Yes | Yes | No  | No  | Similar to `provided`, but you must specify the JAR path manually. **Not recommended** |
| **import**   | ---  | ---  | ---  | ---  | Special scope used only in `<dependencyManagement>` to import a BOM (Bill of Materials) |

#### Scope Examples

**compile** (default - no need to specify):
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

**provided** - The container (e.g., Tomcat) already provides this at runtime:
```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>
```

**runtime** - Not needed for compilation, only for execution:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.2.0</version>
    <scope>runtime</scope>
</dependency>
```

**test** - Only available in `src/test/java`:
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>
```

**import** - Used to import a BOM inside `<dependencyManagement>`:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.2.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

After importing a BOM, you can declare dependencies without specifying versions:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <!-- No version needed, inherited from the BOM -->
    </dependency>
</dependencies>
```

---

### Dependency Management

`<dependencyManagement>` is used to **centralize version control** of dependencies without actually adding them to the project. It is especially useful in multi-module projects.

#### Key Difference: `<dependencies>` vs `<dependencyManagement>`

| Feature | `<dependencies>` | `<dependencyManagement>` |
|---------|:-----------------:|:------------------------:|
| Adds the dependency to the project | Yes | No |
| Defines version centrally | No | Yes |
| Child modules inherit it automatically | Yes | Only if they declare it (without version) |

#### How It Works

**In the parent POM** — define versions centrally:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**In the child module POM** — declare the dependency without version:
```xml
<dependencies>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <!-- Version is inherited from parent's dependencyManagement -->
    </dependency>
</dependencies>
```

This ensures all modules use the **same version** of a dependency, avoiding version conflicts across the project.

---

### Excluding Transitive Dependencies

Sometimes a transitive dependency causes conflicts. You can exclude specific transitive dependencies:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.2.0</version>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

This is a common pattern when you want to replace the default embedded server (Tomcat) with another one (e.g., Jetty or Undertow).

---

### Version Conflicts and Resolution

When two dependencies require **different versions** of the same library, Maven uses the **nearest definition** strategy:
- The version declared **closest to the root** of the dependency tree wins.
- If both are at the same depth, the **first one declared** in the POM wins.

To diagnose version conflicts:
```
mvn dependency:tree -Dverbose
```

This shows the full tree including **omitted duplicates** and **version conflicts**, helping you identify which version Maven selected and why.

To force a specific version, you can:
1. Declare the dependency explicitly in your POM (nearest definition wins).
2. Use `<dependencyManagement>` to override the version.
3. Use `<exclusions>` to remove the conflicting transitive dependency.

---

### Useful Commands

| Command | Description |
|---------|-------------|
| `mvn dependency:tree` | Shows the full dependency tree of the project |
| `mvn dependency:tree -Dverbose` | Shows the tree with conflict details and omitted duplicates |
| `mvn dependency:analyze` | Detects unused declared dependencies and used undeclared dependencies |
| `mvn dependency:resolve` | Displays all resolved dependencies |
