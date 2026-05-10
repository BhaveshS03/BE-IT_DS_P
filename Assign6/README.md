# Assignment 6

## Description
This assignment implements two distributed election algorithms: the Bully Algorithm and the Ring Algorithm. These algorithms elect a coordinator process when the current coordinator fails. The programs simulate the communication between processes and determine the new coordinator based on process IDs.

## Files
- Bully.java : Java implementation of the Bully algorithm.
- Ring.java : Java implementation of the Ring algorithm.

## Requirements
- Java 8

## How to Run

### Java
```bash
# Compile the files
javac Bully.java
javac Ring.java

# Run the Bully algorithm
java Bully

# Run the Ring algorithm
java Ring
```
