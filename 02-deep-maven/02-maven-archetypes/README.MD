## Maven Archetypes

### What is an archetype in Maven?
An archetype in Maven is a pre-built project template that makes it easy to create projects with a specific initial structure. Archetypes are useful for standardizing projects and saving time on initial setup.

### Common types of archetypes

| Archetype                         | Description   |
| --------                          | -------       |
| **maven-archetype-simple**        | A basic project, with no dependencies or tests configured.|
| **maven-archetype-quickstart**    | Basic projects with support for unit testing.|
| **maven-archetype-jar**           | Library-oriented projects that are packaged as JAR files.|
| **maven-archetype-site-simple**   | Projects designed to generate static websites.|
| **maven-archetype-webapp**        | Projects for web applications (WAR-compatible structure).|


**NOTE**: It is not recommended to use archetypes if you are going to work with modern projects such as the latest versions of Java and Spring Boot, because the versions of the archetypes are outdated, for this reason Spring Boot enabled a website (https://start.spring.io/) where you can generate and download pre-configured projects.

