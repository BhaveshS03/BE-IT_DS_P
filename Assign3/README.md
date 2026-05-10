# Assignment 3

## Description
An MPI (Message Passing Interface) application in Java to calculate the sum of an array in a distributed manner. The master process distributes chunks of an array to slave processes using `Scatter`. Each process calculates the partial sum and sends it back to the master process using `Gather`, which then computes the final sum.

## Files
- ArrSum.java : The MPI program for array sum calculation.

## Requirements
- Java 8
- MPJ Express (or another MPI implementation for Java)

## How to Run

### Java
```bash

export MPJ_HOME=./mpj
export PATH=$MPJ_HOME/bin:$PATH
# Compile the file
javac -cp $MPJ_HOME/lib/mpj.jar ArrSum.java

# Run the MPI program with 4 processes
$MPJ_HOME/bin/mpjrun.sh -np 4 ArrSum

```
