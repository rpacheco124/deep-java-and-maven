## Maven Fundaments

### What is Maven
Maven is a **build automation and dependency management** tool for Java projects. It facilitates the management of the project lifecycle, from compilation, testing, packaging, to artifact distribution, as well as managing external libraries a project needs through a configuration file called `pom.xml`.

### Maven's Objectives
Maven's primary goal is to allow a developer to comprehend the complete state of a development effort in the shortest period of time. In order to attain this goal, Maven deals with several areas of concern:

- Making the build process easy
- Providing a uniform build system
- Providing quality project information
- Encouraging better development practices

### Why use Maven  
Maven provides a number of advantages for developers:
- **Dependency management**: Easily include libraries and manage their versions.
- **Standardized Project Structure**: Maven enforces a directory structure that helps organize projects efficiently.
- **Portability**: Maven projects are easy to clone and run on any machine.
- **Consistent builds**: Using a standard build lifecycle ensures repeatability.
- **Centralized configuration**: The `pom.xml` file stores project information in a structured way, making it easier to share across the team.
- **Integration with CI/CD tools**: Maven integrates well with Continuous Integration tools like Jenkins, SonarQube, etc.

### Build Lifecycle  
Maven defines a set of build phases that need to be executed in a specific order. Some of the core phases include:
- **validate**: Validate the project setup.
- **compile**: Compile the source code.
- **test**: Run tests.
- **package**: Package the compiled code into a distributable format (e.g., WAR, JAR).
- **install**: Install the packaged code into the local repository.
- **deploy**: Deploy the package to a remote repository.

#### Full Default Lifecycle (in order)

- **validate**: Validate project structure and required information in the POM.
- **initialize**: Initialize build state (set properties, create folders, prepare environment).
- **generate-sources**: Generate additional source code before compilation.
- **process-sources**: Process generated and main sources before compile.
- **generate-resources**: Generate resource files needed by the application.
- **process-resources**: Copy/filter resources into the build output directory.
- **compile**: Compile main source code (`src/main/java`).
- **process-classes**: Post-process compiled classes (for example bytecode enhancement).
- **generate-test-sources**: Generate test source code.
- **process-test-sources**: Process test source files before compilation.
- **generate-test-resources**: Generate resources required by tests.
- **process-test-resources**: Copy/filter test resources into test output.
- **test-compile**: Compile test source code (`src/test/java`).
- **process-test-classes**: Post-process compiled test classes.
- **test**: Run unit tests (usually with Surefire).
- **prepare-package**: Perform final checks/steps before packaging.
- **package**: Create distributable artifact (`.jar`, `.war`, etc.).
- **pre-integration-test**: Prepare environment for integration tests.
- **integration-test**: Run integration tests (often with Failsafe).
- **post-integration-test**: Clean up integration test environment.
- **verify**: Verify quality gates and validation checks (coverage, rules, etc.).
- **install**: Install artifact into local repository (`~/.m2/repository`).
- **deploy**: Publish artifact to a remote repository.

`initialize` and `verify` are official phases of Maven's default lifecycle. Many external plugins bind goals to these phases.


### Maven Commands
The following are some useful maven commands:

| Command         | Description                                                                 | Lifecycle Stage    |
|-----------------|-----------------------------------------------------------------------------|--------------------|
| `mvn clean`     | Cleans the `target` directory by deleting previously compiled files.       | ---              |
| `mvn dependency:resolve` | Resolves and downloads dependencies declared in `pom.xml` into the local repository. | Goal (`dependency` plugin) |
| `mvn validate`  | Validates the project structure and configuration.                         | **validate**           |
| `mvn compile`   | Compiles the source code of the project.                                   | **compile**            |
| `mvn test`      | Runs unit tests using the compiled code.                                   | **test**               |
| `mvn package`   | Packages the compiled code into a distributable format (e.g., JAR/WAR).    | **package**            |
| `mvn install`   | Installs the package into the local repository for use in other projects.  | **install**            |
| `mvn deploy`    | Copies the packaged code to a remote repository for sharing.              | **deploy**             |

#### Notes:
1) Maven commands like mvn install execute all preceding lifecycle stages in the Build Lifecycle.
2) Custom goals like mvn clean are not part of the default lifecycle but are often used alongside it.
3) You usually do **not** need to run `mvn dependency:resolve` manually: Maven resolves/downloads dependencies automatically when you execute lifecycle phases like `mvn compile`, `mvn test`, `mvn package`, or `mvn install`.

### Maven Repositories

##### **Local** 
- It is a folder on the developer's machine where Maven stores downloaded dependencies and locally built artifacts.
- Maven first looks in the local repository before downloading any dependencies from a remote repository.
- Default location:
  - On **UNIX**/Linux systems: ~/.m2/repository
  - On **Windows**: %USER%\\.m2\repository
- Configurable via the settings.xml file.
  
##### **Remote**
- It is an online server where artifacts shared by third parties or the community (for example, libraries and plugins) are hosted.
- Maven uses remote repositories to download dependencies that are not located in the local repository.
- Popular example:
Maven Central (https://repo.maven.apache.org/maven2) is the default remote repository configured in Maven.
- An additional remote repository can be configured in the pom.xml or settings.xml file.

##### **Private**

- It is a private repository managed within an organization or team.
- It is used to store internal or custom dependencies that are not available in public repositories.
- Popular tools for deploying enterprise repositories:
  - Apache Archiva
  - Sonatype Nexus
  - JFrog Artifactory
  - Gitlab Packages
- Configurable in Maven to look for dependencies in this repository before going to a public remote repository.
