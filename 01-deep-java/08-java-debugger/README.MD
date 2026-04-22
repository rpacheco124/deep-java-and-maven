# Java Debugger

Java also helps us `debug` during the execution of a `.class` file, for this we can use the `jdb` command.

#### NOTES

- To generate the `.class` file it is necessary to add the `-g` flag to enable debug mode in that file.

## Commands used in this folder:

To generate java class with the flag '-g' to enabled debug:
```
javac -g Calculator.java
```
To run debugger:
```
jdb Calculator
```
To run debugger and pass input arguments (example `7` and `12`):
```
jdb Calculator
run 7 12
```
To set breakpoint:
```
stop in Calculator.sum
```
To set breakpoint with line number:
```
stop at Calculator:9
```
To run the java class:
```
run
```
With the where command prints the current line of the code:
```
where
```
With the print command you can print the value of an variable:
```
print a
```
With the cont command you continue execution until the next breakpoint (or program end):
```
cont
```
