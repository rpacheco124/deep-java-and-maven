# How create an executable jar

In this section we are going to create an executable `.jar` file, where we will indicate which is the main file using a `Manifest.mf` file.

#### NOTES

- When we want to generate we will have to use the `-d` flag to indicate the directory where the `.class` files will be generated.

- Now we will use the `jar` command, one of the options of this command helps us to generate a `.jar` file.

- We will use the combined flags of `-c` to create file, `-f` to name the jar file, `-m` to indicate the `Manifest.mf` file and `-C` to indicate the directory from where the .class files will be taken.

## Commands used in this section:

To generate java class:
```
javac -d target $(find src -name "*.java")
```
To generate jar file:
```
jar cfm target/CalculatorApp.jar META-INF/Manifest.mf -C target com/company/CalculatorApp.class
```
To execute the jar file:
```
java -jar target/CalculatorApp.jar
```