# Course: Deep Maven  
**Master the Build System Beyond `mvn clean install`**  

## Description  
This course takes you beyond the basics of Maven. You will understand how the build lifecycle works, how dependencies are resolved and managed, and how to configure real projects from the command line.  

---

## Syllabus  

### 0. Maven Fundamentals  
- What is Maven and why use it.  
- Core concepts: POM file, project structure, build lifecycle.  
- Repositories: local, remote, and private (Nexus/Artifactory).  

### 1. Project Generation  
- Using `mvn archetype:generate` to scaffold projects.  
- Understanding the generated directory structure.  
- Common pitfalls with outdated archetype versions.  

### 2. Maven Archetypes  
- Available archetypes and when (not) to use them.  
- Creating projects from specific archetype templates.  

### 3. Maven Plugins  
- Core plugins vs third-party plugins.  
- Configuring the compiler plugin, Spring Boot plugin, JaCoCo, and more.  
- Binding plugins to lifecycle phases.  

### 4. Maven POM Deep Dive  
- Every section of the `pom.xml` explained.  
- Properties, build configuration, and resource filtering.  

### 5. Dependency Management  
- Dependency scopes (compile, test, provided, runtime, system, import).  
- Transitive dependencies and how Maven resolves them.  
- BOMs (Bill of Materials) and `dependencyManagement`.  
- Conflict resolution strategies.  

### 6. Multi-Module Projects  
- Parent POMs and module inheritance.  
- Reactor builds and module ordering.  
- Sharing configuration across modules.  

### 7. Advanced Topics  
- Maven Wrapper, `settings.xml`, profiles overview, debugging commands.  

---
