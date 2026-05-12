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

## Code Explanation (Line-by-Line)

### 1. `Server.java` (The Socket Server)
```java
import java.net.*; // Import classes for networking (Socket, ServerSocket)
import java.io.*; // Import classes for input/output streams

public class Server {
    public static void main(String[] args) throws Exception {
        // Create a ServerSocket listening on port 5555 for incoming client connections
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("Server Initiated, Waiting for Client to Connect...");

        // accept() blocks execution until a client connects, returning a Socket for communication
        Socket s = ss.accept();
        System.out.println("Client Connected");

        // Set up a BufferedReader to read input from the Server's keyboard (console)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Get the OutputStream from the socket to send data over the network to the client
        OutputStream ostream = s.getOutputStream();

        // Wrap the OutputStream in a PrintWriter with auto-flush enabled (true) for easier string writing
        PrintWriter pw = new PrintWriter(ostream, true);

        // Get the InputStream from the socket to receive incoming data from the client
        InputStream istream = s.getInputStream();

        // Wrap the InputStream in a BufferedReader to read incoming text lines efficiently
        BufferedReader recieve = new BufferedReader(new InputStreamReader(istream));

        // Variables to hold the message strings
        String servermessage = "";
        String clientmessage = "";

        // Continuous loop to keep the chat running
        while (true) {
            // Read a line of text received from the client (blocks until client sends something)
            clientmessage = recieve.readLine();
            System.out.println("Client: " + clientmessage);

            // If the client sends "bye", break out of the loop to end communication
            if (clientmessage.equals("bye")) {
                break;
            }
            
            // Server prompts the user to type a reply
            System.out.print("Server: ");
            servermessage = br.readLine(); // Read input from server's keyboard

            // Send the server's reply over the socket to the client
            pw.println(servermessage);
            
            // If the server typed "bye", break out of the loop
            if (servermessage.equals("bye")) {
                break;
            }
        }

        // Clean up and close all open resources to prevent memory leaks
        s.close(); // Close the client socket
        ss.close(); // Close the server socket
        istream.close(); // Close input stream
        ostream.close(); // Close output stream

        System.out.println("Connection Terminated");
    }    
}
```

### 2. `Client.java` (The Socket Client)
```java
import java.net.*; // Import networking classes
import java.io.*; // Import I/O classes

public class Client {
    public static void main(String[] args) throws Exception {
        // Create a Socket to connect to the server at IP "127.0.0.1" (localhost) on port 5555
        Socket s = new Socket("127.0.0.1", 5555);
        System.out.println("Connected to Server, Please type your message and hit Enter to send");

        // Set up BufferedReader to read input from the Client's keyboard
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Get OutputStream to send data to the Server
        OutputStream ostream = s.getOutputStream();

        // Wrap OutputStream in PrintWriter for easy text writing (auto-flush enabled)
        PrintWriter pw = new PrintWriter(ostream, true);

        // Get InputStream to receive data from the Server
        InputStream istream = s.getInputStream();

        // Wrap InputStream in BufferedReader to read incoming text from the server
        BufferedReader recieve = new BufferedReader(new InputStreamReader(istream));

        // Variables for storing chat messages
        String clientmessage = "";
        String servermessage = "";

        // Continuous loop for the chat
        while (true) {
            // Client prompts user to type a message
            System.out.print("Client: ");
            clientmessage = br.readLine(); // Read from keyboard

            // Send the typed message to the server via the socket
            pw.println(clientmessage);
            
            // If the client typed "bye", break out of the loop
            if (clientmessage.equals("bye")) {
                break;
            }

            // Block and wait to read the server's reply from the socket
            servermessage = recieve.readLine();
            System.out.println("Server: " + servermessage);

            // If the server replied with "bye", break out of the loop
            if (servermessage.equals("bye")) {
                break;
            }
        }

        // Clean up network and I/O resources
        s.close(); // Close socket connection
        istream.close();
        ostream.close();

        System.out.println("Connection Terminated");
    }
}
```
