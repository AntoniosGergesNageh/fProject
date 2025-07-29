package com.VirtualBankingSystem.LoggingService.Service;

import com.VirtualBankingSystem.LoggingService.Entity.LogEntry;
import com.VirtualBankingSystem.LoggingService.Repository.LogEntryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class KafkaConsumerService {

    @Autowired
    private LogEntryRepository logEntryRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "vbank-logs-topic", groupId = "vbank-log-group")
    public void consumeLog(String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);

            LogEntry logEntry = new LogEntry();
            logEntry.setMessageType(rootNode.get("messageType").asText());
            logEntry.setMessage(rootNode.get("message").asText());

            String dateTimeString = rootNode.get("dateTime").asText();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
            logEntry.setDateTime(dateTime);

            logEntryRepository.save(logEntry);

        } catch (Exception e) {
            System.err.println("Failed to process and save log message: " + e.getMessage());
        }
    }
}