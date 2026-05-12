# Assignment 5

## Description
A Java application demonstrating the Token Ring algorithm. The program simulates passing a token and sending data between nodes in a ring topology. The token is passed sequentially until it reaches the sender, who then sends the data to the receiver through intermediate nodes.

## Files
- Tring.java : The token ring simulation program.

## Requirements
- Java 8

## How to Run

### Java
```bash
# Compile the file
javac Tring.java

# Run the program
java Tring
```

## Code Explanation (Line-by-Line)

### `Tring.java` (Token Ring Simulation)
```java
import java.util.Scanner; // Import Scanner class for taking user input

class Tring {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in); // Initialize Scanner to read from standard input

        System.out.print("Enter the number of nodes: ");
        int n = sc.nextInt(); // Read the total number of nodes in the ring

        // Initialize the token at node 0. The token represents the permission to send data.
        int token = 0;

        // Print the initial layout of the ring (e.g., 0 1 2 3 0) to visualize the topology
        for (int i = 0; i < n; i++)
            System.out.print(" " + i);
        System.out.println(" " + 0);

        try {
            while (true) { // Infinite loop to allow continuous data transmission events
                System.out.print("Enter sender: ");
                int s = sc.nextInt(); // Read the ID of the node that wants to send data
                System.out.print("Enter receiver: ");
                int r = sc.nextInt(); // Read the ID of the destination node
                System.out.print("Enter Data: ");
                String d = sc.next(); // Read the data string to be sent

                System.out.print("Token passing:");
                // Pass the token sequentially from the current token holder to the sender
                // i tracks the number of hops, j tracks the current node ID (using modulo n to wrap around the ring)
                for (int i = token, j = token; (i % n) != s; i++, j = (j + 1) % n) {
                    System.out.print(" " + j + "->");
                }
                System.out.println(" " + s); // Token has reached the sender

                System.out.println("Sender " + s + " sending data: " + d);

                // Start forwarding the data from the node immediately after the sender
                // Continue forwarding until the receiver node is reached
                for (int i = (s + 1) % n; i != r; i = (i + 1) % n) {
                    System.out.println("Data " + d + " forwarded by " + i);
                }
                System.out.println("Receiver " + r + " received data: " + d); // Data successfully reached the destination
                
                // Update the token position to the sender, as the sender was the last to hold the token
                token = s;
            }
        } catch (Exception e) {
            // Catch and handle any errors (e.g., invalid input causing exceptions)
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
```
