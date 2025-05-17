package com.example.api.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.api.dto.AppointmentRequest;
import java.util.UUID;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String topic = "appointments";

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    public void send(AppointmentRequest request) {
        try {
            String message = objectMapper.writeValueAsString(request);
            String key = UUID.randomUUID().toString();
            kafkaTemplate.send(topic, key, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
