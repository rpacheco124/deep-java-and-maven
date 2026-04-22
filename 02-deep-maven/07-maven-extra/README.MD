## Maven Extra: Additional Tools and Configuration

---

### 1. Maven Wrapper (mvnw)

The Maven Wrapper is a script that allows you to run Maven **without requiring a global Maven installation**. It ensures that all developers and CI/CD pipelines use the **exact same Maven version**.

#### Why Use the Maven Wrapper?
- **Consistency**: Every developer uses the same Maven version, avoiding "works on my machine" issues.
- **No global installation needed**: New team members can build the project immediately without installing Maven.
- **CI/CD friendly**: Build servers don't need Maven pre-installed.

#### How to Generate the Wrapper

Run this command inside your Maven project:

```
mvn wrapper:wrapper
```

This generates the following files:

```
project/
├── mvnw          (Unix/macOS shell script)
├── mvnw.cmd      (Windows batch script)
└── .mvn/
    └── wrapper/
        └── maven-wrapper.properties  (specifies the Maven version to use)
```

#### How to Use It

Instead of using `mvn`, use `./mvnw` (Unix) or `mvnw.cmd` (Windows):

```
# Unix/macOS
./mvnw clean install

# Windows
mvnw.cmd clean install
```

The first time it runs, it downloads the specified Maven version automatically.

#### The maven-wrapper.properties File

```properties
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip
wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
```

**NOTE**: The wrapper files (`mvnw`, `mvnw.cmd`, and `.mvn/`) should be committed to version control. This is the opposite of what we do with build artifacts — the wrapper is part of the project setup.

---

### 2. Useful Debugging Commands

#### `mvn -X` (Debug Mode)

Runs Maven with **debug-level logging**. This is invaluable when something fails and the standard output doesn't give enough information:

```
mvn clean install -X
```

This shows:
- Every plugin that executes and its configuration
- Dependency resolution details
- Repository lookup order
- Property values being used

**TIP**: The output is very verbose. Redirect it to a file for easier analysis:

```
mvn clean install -X > build-debug.log 2>&1
```

#### `mvn dependency:tree`

Displays the complete dependency tree of your project:

```
mvn dependency:tree
```

Example output:
```
[INFO] com.company:my-app:jar:1.0.0
[INFO] +- org.springframework.boot:spring-boot-starter-web:jar:3.2.0:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter:jar:3.2.0:compile
[INFO] |  |  +- org.springframework.boot:spring-boot:jar:3.2.0:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:3.2.0:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:3.2.0:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:10.1.16:compile
```

Use `-Dverbose` to see conflicts and omitted duplicates:

```
mvn dependency:tree -Dverbose
```

#### `mvn help:effective-pom`

Shows the **Effective POM** — the final POM that Maven actually uses after merging your `pom.xml` with the **Super POM** (Maven's internal default POM) and any parent POMs:

```
mvn help:effective-pom
```

This is extremely useful because it reveals:
- Default plugin versions that Maven uses when you don't specify them
- Default configurations inherited from the Super POM
- All merged properties and settings from parent POMs

**NOTE**: Every `pom.xml` implicitly inherits from the Super POM. The Super POM defines defaults like the Maven Central repository URL, standard directory layout (`src/main/java`, `src/test/java`), and default plugin versions. Running `effective-pom` lets you see this hidden inheritance.

#### Other Useful Commands

| Command | Description |
|---------|-------------|
| `mvn help:effective-settings` | Shows the final merged settings (from global + user settings.xml) |
| `mvn versions:display-dependency-updates` | Shows available newer versions of your dependencies |
| `mvn versions:display-plugin-updates` | Shows available newer versions of your plugins |
| `mvn clean install -DskipTests` | Builds the project skipping test execution (compiles tests but doesn't run them) |
| `mvn clean install -Dmaven.test.skip=true` | Builds the project skipping test compilation and execution entirely |

---

### 3. The settings.xml File

The `settings.xml` file contains Maven configuration that is **not specific to a project** but to the **user or machine**. It is not committed to version control.

#### Location

There are two levels of `settings.xml`:

| Level | Location | Purpose |
|-------|----------|---------|
| **Global** | `${MAVEN_HOME}/conf/settings.xml` | Applies to all users on the machine |
| **User** | `~/.m2/settings.xml` | Applies only to the current user |

If both exist, the user-level settings **override** the global settings.

#### Common Use Cases

**1) Configuring a private/corporate repository (mirror):**

This redirects all dependency downloads through your company's repository manager:

```xml
<settings>
    <mirrors>
        <mirror>
            <id>company-nexus</id>
            <name>Company Nexus Repository</name>
            <url>https://nexus.mycompany.com/repository/maven-public/</url>
            <mirrorOf>*</mirrorOf>
        </mirror>
    </mirrors>
</settings>
```

**2) Configuring credentials for deploying artifacts:**

```xml
<settings>
    <servers>
        <server>
            <id>company-nexus-releases</id>
            <username>deploy-user</username>
            <password>secret-password</password>
        </server>
    </servers>
</settings>
```

The `<id>` must match the `<id>` of the repository declared in your `pom.xml`'s `<distributionManagement>` section.

**3) Defining profiles for different environments:**

```xml
<settings>
    <profiles>
        <profile>
            <id>company</id>
            <repositories>
                <repository>
                    <id>company-releases</id>
                    <url>https://nexus.mycompany.com/repository/releases/</url>
                </repository>
            </repositories>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>company</activeProfile>
    </activeProfiles>
</settings>
```

**4) Configuring proxy for corporate networks:**

```xml
<settings>
    <proxies>
        <proxy>
            <id>company-proxy</id>
            <active>true</active>
            <protocol>https</protocol>
            <host>proxy.mycompany.com</host>
            <port>8080</port>
            <nonProxyHosts>localhost|127.0.0.1</nonProxyHosts>
        </proxy>
    </proxies>
</settings>
```

**NOTE**: Never store passwords in plain text in `settings.xml` in production environments. Maven supports encrypted passwords using `mvn --encrypt-master-password` and `mvn --encrypt-password`. The encrypted values are stored in `~/.m2/settings-security.xml`.

---

### 4. Maven Profiles

Profiles allow you to customize the build for different environments or scenarios. They can be defined in `pom.xml` or `settings.xml`.

#### Defining Profiles in pom.xml

```xml
<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <env>development</env>
            <db.url>jdbc:mysql://localhost:3306/mydb_dev</db.url>
        </properties>
    </profile>

    <profile>
        <id>prod</id>
        <properties>
            <env>production</env>
            <db.url>jdbc:mysql://prod-server:3306/mydb</db.url>
        </properties>
    </profile>
</profiles>
```

#### Activating Profiles

From the command line:
```
mvn clean install -Pdev
mvn clean install -Pprod
```

Profiles can also be activated automatically based on conditions:

```xml
<profile>
    <id>windows-build</id>
    <activation>
        <os>
            <family>Windows</family>
        </os>
    </activation>
    <!-- This profile activates automatically on Windows -->
</profile>
```

```xml
<profile>
    <id>java17</id>
    <activation>
        <jdk>17</jdk>
    </activation>
    <!-- This profile activates automatically when using JDK 17 -->
</profile>
```

#### List Active Profiles

```
mvn help:active-profiles
```

This shows which profiles are currently active and why (explicitly activated, activated by OS, JDK, etc.).
