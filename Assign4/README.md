# Assignment 4

## Description
A Python application demonstrating a clock synchronization algorithm (like Berkeley algorithm) in a distributed system. The server receives time from clients, calculates the average time difference, and sends synchronized times back to clients.

## Files
- server.py : The clock server that accepts clients and synchronizes their clocks.
- client.py : The client process that sends its clock time and receives synchronized time.

## Requirements
- Python 3.x
- python-dateutil (install via `pip install python-dateutil`)

## How to Run

### Python
```bash
# Start the server (in a separate terminal)
python3 server.py

# Start the client (in a separate terminal)
python3 client.py
```

## Code Explanation (Line-by-Line)

### 1. `server.py` (The Clock Server)
```python
# Python3 program imitating a clock server
from dateutil import parser # Used to parse date strings into datetime objects
import threading # For running multiple operations simultaneously (handling clients and syncing)
import datetime # For manipulating dates and times
import socket # For network communication
import time # For pausing execution (sleep)

# datastructure used to store client address and clock data (time diff, socket connection)
client_data = {}

# nested thread function used to receive clock time from a connected client continually
def startReceivingClockTime(connector, address):
    while True:
        # receive clock time string from client
        clock_time_string = connector.recv(1024).decode()
        # convert string back to a datetime object
        clock_time = parser.parse(clock_time_string)
        # Calculate the difference between server's current time and client's time
        clock_time_diff = datetime.datetime.now() - clock_time

        # Update the shared dictionary with this client's latest time information
        client_data[address] = {
            "clock_time": clock_time,
            "time_difference": clock_time_diff,
            "connector": connector # Store socket connection to send data back later
        }

        print("Client Data updated with: " + str(address) + "\n")
        time.sleep(5) # Pause for 5 seconds before waiting for the next update

# master thread function used to open portal for accepting clients over given port
def startConnecting(master_server):
    while True:
        # accept a new client connection (this blocks until a client connects)
        master_slave_connector, addr = master_server.accept()
        slave_address = str(addr[0]) + ":" + str(addr[1])

        print(slave_address + " got connected successfully")

        # Spawn a new thread specifically to handle receiving time from this newly connected client
        current_thread = threading.Thread(
            target=startReceivingClockTime,
            args=(master_slave_connector, slave_address, )
        )
        current_thread.start()

# subroutine function used to fetch average clock difference across all clients
def getAverageClockDiff():
    # Extract just the 'time_difference' for each client into a list
    time_difference_list = list(client['time_difference'] for client_addr, client in client_data.items())
    # Sum all the time differences (starting with 0 difference)
    sum_of_clock_difference = sum(time_difference_list, datetime.timedelta(0, 0))
    # Calculate the average difference
    average_clock_difference = sum_of_clock_difference / len(client_data)
    return average_clock_difference

# master sync thread function used to generate cycles of clock synchronization in the network
def synchronizeAllClocks():
    while True:
        print("New synchronization cycle started.")
        print("Number of clients to be synchronized: " + str(len(client_data)))

        if len(client_data) > 0:
            # Calculate the global average clock difference based on all connected clients
            average_clock_difference = getAverageClockDiff()

            # Iterate over all clients to send them their synchronized time
            for client_addr, client in client_data.items():
                try:
                    # The synchronized time is the server's current time adjusted by the average difference
                    synchronized_time = datetime.datetime.now() + average_clock_difference
                    # Send the synchronized time string to the client
                    client['connector'].send(str(synchronized_time).encode())
                except Exception as e:
                    print("Something went wrong while sending synchronized time through " + str(client_addr))
        else:
            print("No client data. Synchronization not applicable.")

        print("\n\n")
        time.sleep(5) # Wait 5 seconds before the next synchronization cycle

# function used to initiate the Clock Server / Master Node
def initiateClockServer(port=8080):
    # Create a TCP socket
    master_server = socket.socket()
    # Allow the socket to reuse the address (prevents "Address already in use" errors)
    master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    print("Socket at master node created successfully\n")
    # Bind the socket to the port on all available interfaces ('')
    master_server.bind(('', port))

    # Start listening to requests (up to 10 unaccepted connections can queue up)
    master_server.listen(10)
    print("Clock server started...\n")

    print("Starting to make connections...\n")
    # Start a thread that continuously accepts new client connections
    master_thread = threading.Thread(target=startConnecting, args=(master_server, ))
    master_thread.start()

    print("Starting synchronization parallelly...\n")
    # Start a separate thread that periodically synchronizes all connected clients
    sync_thread = threading.Thread(target=synchronizeAllClocks, args=())
    sync_thread.start()

# Driver function
if __name__ == '__main__':
    # Trigger the Clock Server on port 8080
    initiateClockServer(port=8080)
```

### 2. `client.py` (The Client Application)
```python
# Python3 program imitating a client process
from dateutil import parser
import threading
import datetime
import socket
import time

# client thread function used to send time at client side
def startSendingTime(slave_client):
    while True:
        # Get the current time at the client and send it to the server
        slave_client.send(str(datetime.datetime.now()).encode())
        print("Recent time sent successfully\n")
        time.sleep(5) # Wait 5 seconds before sending again

# client thread function used to receive synchronized time
def startReceivingTime(slave_client):
    while True:
        # receive the synchronized time data from the server
        Synchronized_time = parser.parse(slave_client.recv(1024).decode())
        print("Synchronized time at the client is: " + str(Synchronized_time) + "\n")
        # Note: In a real Berkeley implementation, the client's system clock would be updated here.

# function used to Synchronize client process time
def initiateSlaveClient(port=8080):
    # Create a TCP socket
    slave_client = socket.socket()
    # connect to the clock server on local computer at the specified port
    slave_client.connect(('127.0.0.1', port))

    print("Starting to receive time from server\n")
    # Start a thread that periodically sends the client's current time to the server
    send_time_thread = threading.Thread(target=startSendingTime, args=(slave_client, ))
    send_time_thread.start()

    print("Starting to receive synchronized time from server\n")
    # Start a separate thread that continuously listens for synchronized time updates from the server
    receive_time_thread = threading.Thread(target=startReceivingTime, args=(slave_client, ))
    receive_time_thread.start()

# Driver function
if __name__ == '__main__':
    # initialize the Slave / Client on port 8080
    initiateSlaveClient(port=8080)
```
