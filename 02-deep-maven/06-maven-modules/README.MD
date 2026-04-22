# Maven Modules: Key Concepts and Usage

Maven modules are used to structure a project into smaller, manageable pieces while still treating it as a single unit. This is particularly useful for large applications where separating concerns into individual modules improves organization, maintainability, and reusability.

---

## Key Concepts of Maven Modules

### 1. **What Are Maven Modules?**
- A **module** is a sub-project within a larger Maven project.
- The parent project acts as an aggregator for the modules, defining shared configurations and managing the build lifecycle for all modules.

### 2. **Structure of a Multi-Module Project**
A typical Maven multi-module project follows this structure:

    parent-project/ 
            ├── pom.xml (parent POM) 
            ├── module-a/ 
            │   └── pom.xml 
            ├── module-b/ 
            │   └── pom.xml 
            └── module-c/ 
                └── pom.xml

The **parent POM** defines shared dependencies, properties, and plugin configurations.

### 3. **Advantages of Multi-Module Projects**
- **Centralized Configuration**: Shared dependencies and build settings are defined in the parent POM.
- **Separation of Concerns**: Each module can focus on a specific functionality or layer (e.g., service, API, UI).
- **Improved Reusability**: Modules can be reused across projects.
- **Easier Builds**: Maven builds all modules together or selectively builds specific ones.

### 4. **Key Sections in the Parent `pom.xml`**
- **Module Declaration**: Lists all sub-modules in the `<modules>` section:
    ```xml
    <modules>
        <module>module-a</module>
        <module>module-b</module>
        <module>module-c</module>
    </modules>
    ```
- **Dependency Management**: Shared dependency versions are centralized:
    ```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
                <version>3.0.0</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```
- **Shared Properties**: Common settings for all modules:
    ```xml
    <properties>
        <java.version>17</java.version>
    </properties>
    ```

### 5. **Module `pom.xml` files**
Each module has its own pom.xml file and declares the parent:

```xml
<parent>
    <groupId>com.example</groupId>
    <artifactId>parent-project</artifactId>
    <version>1.0.0</version>
</parent>
<artifactId>module-a</artifactId>
```
The module inherits shared configurations from the parent POM.

## How to Create a Multi-Module Project

### 1. **Create the Parent Project**

Create a directory for the parent project and define its `pom.xml` with `<packaging>pom</packaging>`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.company</groupId>
    <artifactId>multi-module-app</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>

    <modules>
        <module>core</module>
        <module>api</module>
    </modules>

    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter</artifactId>
                <version>5.10.1</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

**NOTE**: The `<packaging>pom</packaging>` is required for parent projects. It tells Maven that this project does not produce a JAR or WAR — it only serves as an aggregator and configuration holder for its modules.

### 2. **Create the Child Modules**

Each module has its own `pom.xml` that references the parent:

**core/pom.xml**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.company</groupId>
        <artifactId>multi-module-app</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>core</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <!-- Version inherited from parent's dependencyManagement -->
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

**api/pom.xml**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.company</groupId>
        <artifactId>multi-module-app</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>api</artifactId>

    <dependencies>
        <!-- Dependency on sibling module -->
        <dependency>
            <groupId>com.company</groupId>
            <artifactId>core</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
</project>
```

**NOTE**: Modules can depend on each other. In this example, `api` depends on `core`. Maven resolves the build order automatically based on these inter-module dependencies (this is called the **reactor build order**).

### 3. **Build the Multi-Module Project**

From the **parent directory**, run:

```
mvn clean install
```

Maven will build all modules in the correct order based on their dependencies (reactor order).

---

## Useful Commands for Multi-Module Projects

| Command | Description |
|---------|-------------|
| `mvn clean install` | Builds all modules in reactor order |
| `mvn clean install -pl core` | Builds only the `core` module |
| `mvn clean install -pl api -am` | Builds `api` and **a**lso **m**akes its dependencies (`core`) |
| `mvn clean install -rf :api` | **R**esumes **f**rom the `api` module (useful when a build fails mid-way) |

---

## What Each Module Inherits from the Parent

When a module declares a `<parent>`, it automatically inherits:
- **Properties** (`<properties>`)
- **Dependency versions** from `<dependencyManagement>`
- **Plugin configurations** from `<pluginManagement>`
- **Repositories** configuration
- The **groupId** and **version** (if not overridden in the module)

This means modules only need to declare their own `<artifactId>` and any module-specific configurations.

---

## Real-World Multi-Module Structure

In enterprise applications, a common pattern is to separate concerns into layers:

```
my-application/
├── pom.xml                  (parent - aggregator)
├── my-app-domain/           (entities, value objects)
│   └── pom.xml
├── my-app-repository/       (database access, JPA repositories)
│   └── pom.xml
├── my-app-service/          (business logic)
│   └── pom.xml
├── my-app-api/              (REST controllers, DTOs)
│   └── pom.xml
└── my-app-boot/             (Spring Boot main class, configuration)
    └── pom.xml
```

Each layer depends only on the layers below it, enforcing a clean architecture through Maven module boundaries.

