# Assignment 2

## Description
A CORBA (Common Object Request Broker Architecture) application that provides a string reversal service. The server takes a string and returns its reversed version.

## Files
- ReverseModule.idl : The Interface Definition Language file defining the `Reverse` interface.
- ReverseImpl.java : The implementation of the reverse service.
- ReverseServer.java : The server program that initializes the ORB, POA, and binds the servant.
- ReverseClient.java : The client program that prompts the user for a string and calls the remote service.

## Requirements
- Java 8

## How to Run

### Java
```bash
# Compile the IDL file
idlj -fall ReverseModule.idl

# Compile the Java files
javac *.java ReverseModule/*.java

# Start the CORBA Naming Service (in a separate terminal)
orbd -ORBInitialPort 1050

# Start the server (in a separate terminal)
java ReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost

# Run the client
java ReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost
```
