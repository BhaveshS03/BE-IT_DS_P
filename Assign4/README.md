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
