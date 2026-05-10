# Assignment 1

## Description
A simple RMI (Remote Method Invocation) application in Java that provides an addition service. The server exposes an `add` method, and the client connects to the server to pass two numbers and receive their sum.

## Files
- AddServerIntf.java : The remote interface defining the `add` method.
- AddServerImpl.java : The implementation of the remote interface.
- AddServer.java : The server program that creates and binds the remote object.
- AddClient.java : The client program that looks up the remote object and invokes the `add` method.

## Requirements
- Java 8

## How to Run

### Java
```bash
# Compile all files
javac *.java

# Start the RMI registry (in a separate terminal)
rmiregistry

# Start the server (in a separate terminal)
java AddServer

# Run the client
java AddClient localhost 5 10
```
