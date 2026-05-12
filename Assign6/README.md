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

## Code Explanation (Line-by-Line)

### 1. `Bully.java` (Bully Algorithm)
```java
import java.util.*; // Import utility classes like Scanner

public class Bully {
    int coordinator; // Stores the ID of the current coordinator process
    int max_processes; // Stores the total number of processes created
    boolean processes[]; // Array to track the status (up=true/down=false) of each process

    public Bully(int max) {
        max_processes = max;
        processes = new boolean[max_processes]; // Initialize boolean array
        coordinator = max; // Initially, the process with the highest ID is the coordinator

        System.out.println("Creating processes..");
        // Initialize all processes to be 'up' (true) at the start
        for (int i = 0; i < max; i++) {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created");
        }
        System.out.println("Process P" + coordinator + " is the coordinator");
    }

    void displayProcesses() {
        // Loop through the boolean array and print whether each process is up or down
        for (int i = 0; i < max_processes; i++) {
            if (processes[i]) {
                System.out.println("P" + (i + 1) + " is up");
            } else {
                System.out.println("P" + (i + 1) + " is down");
            }
        }
        System.out.println("Process P" + coordinator + " is the coordinator");
    }

    void upProcess(int process_id) {
        // Bring a process back up if it is currently down (process ID is 1-based, array is 0-based)
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process " + process_id + " is now up.");
        } else {
            System.out.println("Process " + process_id + " is already up.");
        }
    }

    void downProcess(int process_id) {
        // Mark a process as down (failed/crashed)
        if (!processes[process_id - 1]) {
            System.out.println("Process " + process_id + " is already down.");
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process " + process_id + " is down.");
        }
    }

    void runElection(int process_id) {
        coordinator = process_id; // Temporarily assume the initiating process is the coordinator
        boolean keepGoing = true; // Flag to determine if election messages should continue

        // The initiating process sends election messages to all processes with HIGHER IDs
        for (int i = process_id; i < max_processes && keepGoing; i++) {
            System.out.println("Election message sent from process " + process_id + " to process " + (i + 1));
            
            // If a higher ID process is active (up), it "bullies" the lower process and takes over the election
            if (processes[i]) {
                keepGoing = false; // Stop the lower process from continuing its election broadcast
                runElection(i + 1); // The higher process recursively starts its own election broadcast
            }
        }
        // When recursion unwinds, the highest active process will have successfully claimed the coordinator role
    }

    public static void main(String args[]) {
        // Standard menu-driven main method
        // Allows user to trigger process creation, crash nodes, and initiate elections
    }
}
```

### 2. `Ring.java` (Ring Algorithm)
```java
import java.util.*; // Import ArrayList, Collections, Scanner

public class Ring {
    int max_processes; // Total number of processes in the logical ring
    int coordinator; // ID of the current coordinator
    boolean processes[]; // Array to track active/failed processes
    ArrayList<Integer> pid; // List to store active process IDs encountered during election message traversal

    public Ring(int max) {
        // ... (Initialization similar to Bully: all processes start as 'up', highest ID is coordinator)
        // ...
    }

    // displayProcesses(), upProcess(), downProcess() are identical in logic to the Bully algorithm

    void displayArrayList(ArrayList<Integer> pid) {
        // Helper function to print the list of active processes collected during an election
        System.out.print("[ ");
        for (Integer x : pid) {
            System.out.print(x + " ");
        }
        System.out.print(" ]\n");
    }

    void initElection(int process_id) {
        // Only an active process can initiate an election
        if (processes[process_id - 1]) {
            pid.add(process_id); // The initiator adds its own ID to the active list
            int temp = process_id; // temp is used to traverse the logical ring

            System.out.print("Process P" + process_id + " sending the following list:- ");
            displayArrayList(pid); // Print the current list being sent to the neighbor

            // Pass the election message around the ring until it reaches the process just before the initiator
            while (temp != process_id - 1) {
                // If the next process in the ring is active
                if (processes[temp]) {
                    pid.add(temp + 1); // Add its ID to the active list
                    System.out.print("Process P" + (temp + 1) + " sending the following list:- ");
                    displayArrayList(pid); // Simulate passing the updated list to the next neighbor
                }
                // Move to the next process in the ring using modulo arithmetic to wrap around
                temp = (temp + 1) % max_processes;
            }
            // Once the list has traversed the full ring back to the initiator,
            // the new coordinator is elected by finding the maximum ID in the active list
            coordinator = Collections.max(pid);
            
            System.out.println("Process P" + process_id + " has declared P" + coordinator + " as the coordinator");
            pid.clear(); // Clear the list for future elections
        }
    }

    public static void main(String args[]) {
        // Standard menu-driven main method
    }
}
```
