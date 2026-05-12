
## Distributed Systems Assignments of SPPU Final Year IT Syllabus (2019 pattern)


| Assignment No. | Problem Statement  |
| :--------------:  | :-------------- |
|[Assignment 1](Assign1)| Implement multi-threaded client/server Process communication using RMI.| 
|[Assignment 2](Assign2)| Develop distributed application using CORBA to demonstrate object brokering (Calculator or String operations).|
|[Assignment 3](Assign3)| Develop a distributed system, to find sum of N elements in an array by distributing N/n elements to n number of processors MPI or OpenMP. Demonstrate by displaying the intermediate sums calculated at different processors. |
|[Assignment 4](Assign4)| Implement Berkeley algorithm for clock synchronization.|
|[Assignment 5](Assign5)| Implement token ring based mutual exclusion algorithm.|
|[Assignment 6](Assign6)| Implement Bully and Ring algorithm for leader election.|
|[Assignment 7](Assign7)| Create a simple web service and write distributed application(calculator) to consume the Web Service.|
|[Extra](Sockets)| Develop any distributed application for implementing client-server communication programs based on Java Sockets.|

## Execution Steps


### Pre-requisites:

1. Install JDK-8

        sudo apt-get remove openjdk*
        sudo apt update
        sudo apt install openjdk-8-jdk openjdk-8-jre
    
2. Download [MPJ Express](https://sourceforge.net/projects/mpjexpress/files/releases/mpj-v0_44.tar.gz/download) and extract in the Downloads dir         
 
 3. Install Apache Netbeans
            
         sudo apt update && sudo apt upgrade
         sudo snap install netbeans --classic
     Glassfish server version must be 4.1.1
    
    


### Assignment 1:

**Explanation:** Uses Java RMI (Remote Method Invocation) to allow a client to invoke methods on a remote server object. The server implements an interface for addition, which the client calls remotely.

Terminal 1:

    javac *.java
    rmic AddServerImpl

Terminal 2:

    rmiregistry

Terminal 3:

    java AddServer

Terminal 4:

    java AddClient 127.0.0.1 5 8

### Assignment 2:

**Explanation:** Uses CORBA (Common Object Request Broker Architecture) to facilitate communication between a client and a server. The server provides a string reversal service via an IDL interface, which the client consumes.

Terminal 1:

    idlj -fall ReverseModule.idl
    javac *.java ReverseModule/*.java
    orbd -ORBInitialPort 1056&
    java ReverseServer -ORBInitialPort 1056& 

Terminal 2:

    java ReverseClient -ORBInitialPort 1056 -ORBInitialHost localhost

### Assignment 3:

**Explanation:** Uses MPJ Express (MPI for Java) to distribute an array summing task across multiple processors. The master scatters array chunks to workers, which compute local sums, and then the master gathers these to compute the total.

Terminal:

    export MPJ_HOME=/home/ubuntu/Downloads/mpj-v0_44
    export PATH=$MPJ_HOME/bin:$PATH
    javac -cp $MPJ_HOME/lib/mpj.jar ArrSum.java
    $MPJ_HOME/bin/mpjrun.sh -np 4 ArrSum

### Assignment 4:

**Explanation:** Implements the Berkeley algorithm for clock synchronization using Python. A time daemon (server) polls clients for their times, computes the average difference, and instructs clients to adjust their clocks to stay synchronized.

Terminal 1:

    python client.py


Terminal 2:

    python server.py

### Assignment 5:

**Explanation:** Implements the Token Ring mutual exclusion algorithm in Java. Processes are organized in a logical ring and pass a token. A process can only enter its critical section when it holds the token.

Terminal

    javac Tring.java
    java Tring

### Assignment 6:

**Explanation:** Implements the Bully and Ring leader election algorithms. These algorithms elect a new coordinator when the current one fails, typically based on the highest process ID.

Terminal

    javac Bully.java
    java Bully
    javac Ring.java
    java Ring
    
### Assignment 7:

**Explanation:** Creates a simple web service and a client application (calculator) to consume it, demonstrating basic Web Services/SOA principles.

   [Youtube tutorial](https://www.youtube.com/watch?v=0z-HvSfr-M4)
    
### Extra:

**Explanation:** Implements a basic client-server communication system using Java Sockets. Demonstrates low-level network programming with TCP/IP.

Terminal 1:

    javac *.java
    java Server
 Reminal 2:
 
    java Client
    
    
