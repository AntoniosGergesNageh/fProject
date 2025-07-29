# Virtual Banking System - Setup and Usage Guide

## Overview
This project is a virtual banking system with microservices architecture, consisting of:
- User Service: Handles user authentication and profile management
- Account Service: Manages banking accounts and balances
- Transaction Service: Processes financial transactions
- Logging Service: Handles system-wide logging
- BFF (Backend For Frontend) Service: Acts as an API gateway for the front-end

## Requirements
- Java 17 or higher
- MySQL Database (pre-configured)
- Web browser

## Setup Instructions

### Step 1: Database Setup
Ensure your MySQL server is running and the following databases are created:
- account_service_db
- user_service_db
- transaction_service_db
- logging_service_db

### Step 2: Start Services
1. Use the provided batch file to start all services:
   - Double-click on `start_services.bat` in the project root directory
   - This will start all the microservices in separate terminal windows
   - Wait for all services to initialize completely (this may take a few minutes)

### Step 3: Access the Front-end
1. Navigate to the `front-end/front-end` directory
2. Open `Ejada Login Page.html` in your web browser
3. The system is now ready to use

## Using the Application

### First-time Users
1. Click on "Login" on the welcome page
2. Click "Register" to create a new account
3. Fill in your details and submit

### Returning Users
1. Enter your username and password on the login page
2. Click "Login" to access your dashboard

### Features
- **Dashboard**: View account summaries and recent transactions
- **Create Account**: Add new bank accounts (checking, savings)
- **Transfer Money**: Send funds between accounts
- **Account Details**: View detailed account information

## Troubleshooting
- If any service fails to start, check the terminal output for errors
- Make sure the database connection details are correct in the application.properties files
- If the front-end can't connect to the services, verify that the BFF service is running on port 9099

## Architecture Overview
- **Front-end**: HTML/CSS/JavaScript
- **API Gateway**: BFF Service (Spring Boot)
- **Microservices**: Account, User, Transaction, Logging (All Spring Boot)
- **Database**: MySQL
- **Communication**: RESTful APIs between services
