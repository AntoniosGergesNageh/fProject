package com.VirtualBankingSystem.AccountService.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    /**
     * Creates a KafkaTemplate bean that can be injected into other components.
     * The template is the primary API for sending messages to Kafka.
     *
     * @param producerFactory The factory that creates Kafka producer instances. Spring Boot
     * configures this automatically based on your application.properties.
     * @return A configured KafkaTemplate instance.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}