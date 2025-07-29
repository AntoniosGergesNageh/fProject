#!/bin/bash

# Start Virtual Banking System Services
echo "Starting all Virtual Banking System Services..."

# Change directory to the root of the project
cd $(dirname "$0")

# Start services in separate terminals (if available)
# For Windows PowerShell, you would need to modify this

# Start AccountService
start powershell.exe -NoExit -Command "cd .\AccountService\AccountService; ./mvnw.cmd spring-boot:run"
echo "Started AccountService"

# Start UserService
start powershell.exe -NoExit -Command "cd .\UserService\UserService; ./mvnw.cmd spring-boot:run"
echo "Started UserService"

# Start TransactionService
start powershell.exe -NoExit -Command "cd .\TransactionService\TransactionService; ./mvnw.cmd spring-boot:run"
echo "Started TransactionService"

# Start LoggingService
start powershell.exe -NoExit -Command "cd .\LoggingService; ./mvnw.cmd spring-boot:run"
echo "Started LoggingService"

# Start BFF Service (which connects to all other services)
start powershell.exe -NoExit -Command "cd .\BFFService; ./mvnw.cmd spring-boot:run"
echo "Started BFF Service"

# Wait for all services to start
echo "All services are starting. Please wait for them to initialize completely."
echo "Access the front-end by opening the Ejada Login Page.html file in the front-end folder."

# End
echo "Services startup script completed."
