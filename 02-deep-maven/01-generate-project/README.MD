## Generate Maven Project

To create a Maven project, you use the **mvn archetype:generate** command, which allows you to generate a project structure based on an archetype. Here are the steps and details:

```
mvn archetype:generate
```

This command launches an interactive wizard where Maven asks for information about the project and the desired archetype.

Common parameters when generating a project:
- **DgroupId**: Defines the group identifier of the project (usually the reverse domain of your organization or application, such as com.mycompany).
- **DartifactId**: This is the unique name of the project or module.
- **Dversion**: Defines the version of the project. By default, it is 1.0-SNAPSHOT.
- **DarchetypeArtifactId**: Specifies the archetype to be used as a template.

We can send the project information using parameters as in the following command:

```
mvn archetype:generate \
  -DgroupId=com.dachser \
  -DartifactId=simple-maven \
  -Dversion=1.0 \
  -DarchetypeArtifactId=maven-archetype-simple \
  -DarchetypeVersion=1.5 \
  -DinteractiveMode=false
```

Maven generates the project with the following structure:

![](./assets/maven-project-structure.png)