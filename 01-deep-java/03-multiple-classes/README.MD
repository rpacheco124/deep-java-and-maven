# Java Compile Multiple java files

In this section we will see how to compile multiple java files respecting the package structure. 

#### NOTES

- When we want to generate we will have to use the `-d` flag to indicate the directory where the `.class` files will be generated.

- When we want to run it, we have to use the `-cp` flag to indicate the directory where the `.class` files are located.

## Commands used in this section:

To generate java class:
```
javac -d target $(find src -name "*.java")
```
To execute java class:
```
java -cp target com.dachser.MultipleClassesApp
```