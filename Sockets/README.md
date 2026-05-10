# Sockets Assignment

## Description
A simple Java client-server application demonstrating bidirectional socket communication. The server and client can exchange messages sequentially. Typing 'bye' from either end terminates the connection.

## Files
- Server.java : The server program that opens a port and waits for client connections.
- Client.java : The client program that connects to the server and starts sending messages.

## Requirements
- Java 8

## How to Run

### Java
```bash
# Compile the files
javac Server.java
javac Client.java

# Run the server (in a separate terminal)
java Server

# Run the client (in a separate terminal)
java Client
```
