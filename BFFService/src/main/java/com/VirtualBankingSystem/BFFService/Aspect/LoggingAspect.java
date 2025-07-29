package com.VirtualBankingSystem.BFFService.Aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String KAFKA_TOPIC = "vbank-logs-topic";

    /**
     * Pointcut that matches all public methods in any class annotated with @RestController.
     * This is our target for interception.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    /**
     * Advice that runs BEFORE the execution of any method matched by the controllerMethods() pointcut.
     * It logs the incoming HTTP request body.
     *
     * @param joinPoint provides access to information about the intercepted method.
     */
    @Before("controllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        try {
            if (joinPoint.getArgs().length > 0) {
                Object requestBody = joinPoint.getArgs()[0];

                ObjectNode logNode = objectMapper.createObjectNode();
                logNode.put("message", "[BFF-Service] Request: " + objectMapper.writeValueAsString(requestBody));
                logNode.put("messageType", "Request");
                logNode.put("dateTime", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

                kafkaTemplate.send(KAFKA_TOPIC, logNode.toString());
            }
        } catch (Exception e) {
            System.err.println("Error logging request: " + e.getMessage());
        }
    }

    /**
     * Advice that runs AFTER a method matched by controllerMethods() returns successfully.
     * It logs the outgoing HTTP response body.
     *
     * @param joinPoint provides access to information about the intercepted method.
     * @param response  the object returned by the controller method.
     */
    @AfterReturning(pointcut = "controllerMethods()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) {
        try {
            ObjectNode logNode = objectMapper.createObjectNode();
            logNode.put("message", "[BFF-Service] Response: " + objectMapper.writeValueAsString(response));
            logNode.put("messageType", "Response");
            logNode.put("dateTime", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

            kafkaTemplate.send(KAFKA_TOPIC, logNode.toString());
        } catch (Exception e) {
            System.err.println("Error logging response: " + e.getMessage());
        }
    }
}
