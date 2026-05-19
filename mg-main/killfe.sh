#!/bin/bash

port=4200

# Find the process using the specified port
process=$(netstat -tulnp | grep ":$port " | awk '{print $7}' | cut -d'/' -f1)

if [ -n "$process" ]; then
    echo "Process found with port $port. Killing the process..."
    kill -9 $process
    echo "Process with ID $process has been terminated."
else
    echo "No process found with port $port."
fi
