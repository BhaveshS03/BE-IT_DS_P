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

## Code Explanation (Line-by-Line)

### 1. `AddServerIntf.java` (The Remote Interface)
```java
import java.rmi.*; // Imports classes for Remote Method Invocation (RMI)

public interface AddServerIntf extends Remote { // Interface must extend java.rmi.Remote to be accessible remotely
    // Method declaration that can be invoked remotely
    // Must throw RemoteException to handle network/communication errors
    double add(double d1, double d2) throws RemoteException;
}
```

### 2. `AddServerImpl.java` (The Implementation)
```java
import java.rmi.*;
import java.rmi.server.*; // Imports classes for server-side RMI operations

// Extends UnicastRemoteObject to automatically export the remote object, making it available to receive incoming calls
public class AddServerImpl extends UnicastRemoteObject implements AddServerIntf {
    
    // Default constructor must throw RemoteException because the superclass (UnicastRemoteObject) constructor throws it
    public AddServerImpl() throws RemoteException {
        super(); // Implicitly calls the superclass constructor to export the object
    }

    // Actual implementation of the remote method defined in the interface
    public double add(double d1, double d2) throws RemoteException {
        return d1 + d2; // Returns the sum of the two numbers
    }
}
```

### 3. `AddServer.java` (The Server Application)
```java
import java.rmi.*;

public class AddServer {
    public static void main(String args[]) {
        try {
            // Create an instance of the remote object implementation
            AddServerImpl addServerImpl = new AddServerImpl();
            
            // Bind (register) the remote object to the RMI registry with the name "AddServer"
            // The client will use this name to look up the object
            Naming.rebind("AddServer", addServerImpl);
            
        } catch (Exception e) {
            // Catch and print any exceptions (like if rmiregistry isn't running)
            System.out.println("Exception: " + e);
        }
    }
}
```

### 4. `AddClient.java` (The Client Application)
```java
import java.rmi.*;

public class AddClient {
    public static void main(String args[]) {
        try {
            // Construct the URL to look up the remote object. 
            // args[0] is the server address (e.g., localhost) and "AddServer" is the bound name
            String addServerURL = "rmi://" + args[0] + "/AddServer";
            
            // Look up the remote object in the RMI registry and cast it to the interface type
            AddServerIntf addServerIntf = (AddServerIntf) Naming.lookup(addServerURL);

            // Parse the first number from command line arguments
            System.out.println("The first number is: " + args[1]);
            double d1 = Double.parseDouble(args[1]);

            // Parse the second number from command line arguments
            System.out.println("The second number is: " + args[2]);
            double d2 = Double.parseDouble(args[2]);

            // Invoke the remote method 'add' on the server and print the result
            System.out.println("The sum is: " + addServerIntf.add(d1, d2));
            
        } catch (Exception e) {
            // Catch and print any exceptions (e.g., connection issues)
            System.out.println("Exception: " + e);
        }
    }
}
```
