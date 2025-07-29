# BFFService Logging Integration Setup

## Overview
I have successfully integrated centralized logging functionality into the BFFService. The logging system uses Apache Kafka to send log messages to a centralized LoggingService that stores them in a MySQL database.

## What Has Been Implemented

### 1. Dependencies Added
- **Spring Boot AOP Starter**: Added to `pom.xml` for aspect-oriented programming support
- **Jackson Databind**: Added to `pom.xml` for JSON processing

### 2. Configuration Files Updated
- **application.properties**: Added Kafka producer configuration
  ```properties
  # Kafka Producer Configuration
  spring.kafka.producer.bootstrap-servers=localhost:9092
  spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
  spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
  ```

### 3. New Classes Created
- **KafkaProducerConfig.java**: Configuration class for Kafka producer
- **LoggingAspect.java**: AOP aspect that intercepts REST controller methods and logs requests/responses

### 4. Main Application Class Updated
- Added `@EnableDiscoveryClient` annotation for Eureka integration

## Logging Features

### Automatic Request/Response Logging
The LoggingAspect automatically logs:
- **HTTP Requests**: All incoming requests to REST controllers
- **HTTP Responses**: All outgoing responses from REST controllers
- **Timestamps**: Each log entry includes ISO formatted timestamp
- **Service Identification**: Log messages are prefixed with "[BFF-Service]" for easy identification

### Log Message Format
```json
{
  "message": "[BFF-Service] Request: {request_body}",
  "messageType": "Request",
  "dateTime": "2025-07-29T10:30:45"
}
```

## Prerequisites for Running

### 1. Infrastructure Services
Before running the BFFService, ensure these services are running:

#### Apache Kafka
```bash
# Start Zookeeper (usually on port 2181)
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka (usually on port 9092)
bin/kafka-server-start.sh config/server.properties
```

#### MySQL Database
- Ensure MySQL is running on `localhost:3306`
- Create database `log_service_db` for the LoggingService
- User: `Ejada-Virtual-Banking`
- Password: `tony1944`

#### Service Registry (Eureka)
- Start Eureka Server on `localhost:8761`

### 2. LoggingService
Start the LoggingService to consume and store log messages:
```bash
cd LoggingService
./mvnw spring-boot:run
```

### 3. Other Services (Optional)
For full integration testing, you may want to run:
- AccountService (port 9091)
- UserService
- TransactionService

## Running the BFFService

### Option 1: Using Maven Wrapper
```bash
cd BFFService
./mvnw.cmd spring-boot:run
```

### Option 2: Using VS Code Task
Use the "Run BFFService" task if available in VS Code tasks.

## Service Configuration

### Port Configuration
- **BFFService**: `localhost:9099`
- **LoggingService**: `localhost:9095`
- **Kafka**: `localhost:9092`
- **Eureka**: `localhost:8761`

### Kafka Topic
- **Topic Name**: `vbank-logs-topic`
- **Consumer Group**: `vbank-log-group`

## Testing the Logging

### 1. Start Required Services
1. Start Kafka
2. Start MySQL
3. Start Eureka Server
4. Start LoggingService
5. Start BFFService

### 2. Make API Calls
Send requests to any BFFService REST endpoints. The logging aspect will automatically:
- Log the request body to Kafka
- Log the response body to Kafka

### 3. Verify Logs
Check the MySQL database `log_service_db` table `vbank_logs` to see the stored log entries.

## Troubleshooting

### Common Issues

#### Kafka Connection Issues
- Verify Kafka is running on `localhost:9092`
- Check Kafka topics exist: `bin/kafka-topics.sh --list --bootstrap-server localhost:9092`

#### Database Connection Issues
- Verify MySQL is running and accessible
- Check database credentials in `application-secrets.properties`
- Ensure `log_service_db` database exists

#### LoggingService Not Consuming
- Verify LoggingService is running
- Check Kafka consumer group configuration
- Monitor LoggingService logs for errors

#### No Logs Being Generated
- Verify the LoggingAspect is being applied (check for @RestController methods)
- Check BFFService application logs for any AOP-related errors
- Ensure Jackson can serialize your request/response objects

## Architecture Flow

```
HTTP Request → BFFService REST Controller → LoggingAspect (Before)
                                        ↓
                                   Kafka Producer → vbank-logs-topic
                                                          ↓
                                                   LoggingService Consumer
                                                          ↓
                                                   MySQL Database (vbank_logs table)

HTTP Response ← BFFService REST Controller ← LoggingAspect (After)
                                        ↓
                                   Kafka Producer → vbank-logs-topic
                                                          ↓
                                                   LoggingService Consumer
                                                          ↓
                                                   MySQL Database (vbank_logs table)
```

## Notes
- The logging is non-intrusive and doesn't affect the normal operation of the BFFService
- Log messages are sent asynchronously to Kafka
- All configuration follows the same patterns used in other services (AccountService, UserService)
- The setup is production-ready and scalable
