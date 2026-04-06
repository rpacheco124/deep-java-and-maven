# Deep Java and Maven Course (Without IDE)

Master the Java ecosystem and Maven build system by doing everything manually from the command line — no IDE shortcuts, no magic, just real understanding.

---

## Who Is This Course For?

This course is designed for **Java developers with prior experience** who want to understand what happens behind the scenes. If you've been using an IDE and Maven without fully understanding what they do for you, this course will fill those gaps.

### Prerequisites
- Basic Java programming knowledge (classes, interfaces, inheritance)
- A terminal/command line available
- JDK 17+ installed (`java -version` and `javac -version` should work)
- Maven 3.8+ installed (`mvn -version` should work)

---

## Course Structure

The course is divided into two main parts. It is recommended to follow them in order, as Part 2 builds on the concepts learned in Part 1.

### Part 1: Java from the Ground Up

Learn how Java works internally by performing manually what IDEs and build tools automate for you.

| Module | Topic | What You'll Learn |
|--------|-------|-------------------|
| [00 - Java Fundamentals](./01-deep-java/00-java-fundaments/) | JDK, JRE, JVM | The architecture of the Java platform and the role of each component |
| [01 - Hello World](./01-deep-java/01-hello-world/) | Compile & Run | How `javac` compiles and `java` executes, inspecting `.class` files with `javap` |
| [02 - Hello World Args](./01-deep-java/02-hello-world-args/) | Command-line Arguments | How the `String[] args` parameter works |
| [03 - Multiple Classes](./01-deep-java/03-multiple-classes/) | Packages & Structure | Creating a real project structure with packages and compiling multiple files |
| [04 - Create Library](./01-deep-java/04-create-library/) | Building JARs | Creating reusable `.jar` libraries with the `jar` command |
| [05 - Import Libraries](./01-deep-java/05-import-and-use-libraries/) | Classpath | Using external JARs and understanding the classpath |
| [06 - Executable JAR](./01-deep-java/06-executable-jar/) | Manifest & Packaging | Creating executable JARs with `Manifest.mf` |
| [07 - Javadoc](./01-deep-java/07-javadoc/) | Documentation | Generating HTML documentation from code comments |
| [08 - Java Debugger](./01-deep-java/08-java-debugger/) | Debugging with jdb | Debugging Java applications from the command line |
| [09 - Reflection Inspect](./01-deep-java/09-java-reflection-inspect/) | Reflection API | Inspecting classes, methods, and fields at runtime |
| [10 - Reflection Invoke](./01-deep-java/10-java-reflection-invoke/) | Reflection API | Dynamically invoking methods at runtime |
| [11 - Reflection Load](./01-deep-java/11-java-reflection-load/) | Dynamic Loading | Loading classes dynamically — how frameworks load plugins |

### Part 2: Deep Maven

Understand Maven beyond `mvn clean install`. Learn how the build system works, how dependencies are resolved, and how to configure projects properly.

| Module | Topic | What You'll Learn |
|--------|-------|-------------------|
| [00 - Maven Fundamentals](./02-deep-maven/00-maven-fundaments/) | Core Concepts | What Maven is, build lifecycle, repositories (local/remote/private) |
| [01 - Generate Project](./02-deep-maven/01-generate-project/) | Project Creation | Using `mvn archetype:generate` and understanding the generated structure |
| [02 - Maven Archetypes](./02-deep-maven/02-maven-archetypes/) | Templates | Available archetypes and when (not) to use them |
| [03 - Maven Plugins](./02-deep-maven/03-maven-plugins/) | Build Plugins | Core vs third-party plugins, configuring compiler, Spring Boot, JaCoCo |
| [04 - Maven POM](./02-deep-maven/04-maven-pom/) | pom.xml Deep Dive | Every section of the POM file explained |
| [05 - Dependencies](./02-deep-maven/05-maven-dependencies/) | Dependency Management | Scopes, transitive dependencies, BOMs, conflict resolution |
| [06 - Maven Modules](./02-deep-maven/06-maven-modules/) | Multi-Module Projects | Parent POMs, module inheritance, reactor builds |
| [07 - Maven Extra](./02-deep-maven/07-maven-extra/) | Advanced Topics | Maven Wrapper, settings.xml, profiles, debugging commands |

---

## How to Use This Course

1. **Follow the order**: Each module builds on the previous one.
2. **Type the commands yourself**: Don't copy-paste. The muscle memory of typing `javac`, `java -cp`, and `jar cf` is part of the learning.
3. **Read the README first**: Each module has a `README.MD` with the theory and commands.
4. **Experiment**: After completing a module, try modifying the code and see what breaks. Understanding errors is as valuable as understanding success.

---

## Key Tools Used in This Course

| Tool | Purpose |
|------|---------|
| `javac` | Java compiler (source code to bytecode) |
| `java` | JVM launcher (executes bytecode) |
| `javap` | Class file disassembler (inspect bytecode) |
| `jar` | Archive tool (create/extract JAR files) |
| `javadoc` | Documentation generator |
| `jdb` | Java debugger |
| `mvn` | Maven build tool |
