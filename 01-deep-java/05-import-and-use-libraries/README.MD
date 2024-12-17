# Import and use the Java libraries

In this section we are going to generate a library so that it can be used in other Java projects.

#### NOTES

- When we want to generate we will have to use the `-d` flag to indicate the directory where the `.class` files will be generated and `-cp` to indicate the path of the `.jar` file (external library).

- To run we use the `-cp` flag to indicate the path of the `.class` files where we will also include the external library, the `.jar` file.

## Commands used in this section:

To generate java class:
```
javac -d target -cp libs/greeting.jar src/com/dachser/App.java
```
To execute java class:
```
java -cp target;libs/greeting.jar com.dachser.App
```