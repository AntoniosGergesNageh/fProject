@echo off
echo Starting all services for Virtual Banking System...

echo Starting BFF Service...
start cmd /k "cd BFFService && mvnw spring-boot:run"

echo Starting Account Service...
start cmd /k "cd AccountService\AccountService && mvnw spring-boot:run"

echo Starting Transaction Service...
start cmd /k "cd TransactionService\TransactionService && mvnw spring-boot:run"

echo Starting User Service...
start cmd /k "cd UserService\UserService && mvnw spring-boot:run"

echo Starting Logging Service...
start cmd /k "cd LoggingService && mvnw spring-boot:run"

echo All services are starting. Please wait a few moments for them to initialize.
echo You can now open the frontend HTML files in your browser.

echo To open the registration page directly, press any key...
pause
start "" "front-end\front-end\Ejada Register Page.html"
