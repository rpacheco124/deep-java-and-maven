# Java Create Library

In this section we are going to generate a library so that it can be used in other Java projects.

#### NOTES

- When we want to generate we will have to use the `-d` flag to indicate the directory where the `.class` files will be generated.

- Now we will use the `jar` command, one of the options of this command helps us to generate a `.jar` file.

- A `.jar` file packages one or many `.class` files, this helps us to export/share functionalities between java projects.

- We will use the combined flags of `-c` to create file, `-f` to name the jar file and `-C` to indicate the directory from where the .class files will be taken.

## Commands used in this section:

To generate java class:
```
javac -d target $(find src -name "*.java")
```
To generate jar file:
```
jar cf greeting.jar -C target com/company/greeting
```