package com.example.api.controller;

import com.example.api.dto.AppointmentRequest;
import com.example.api.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final KafkaProducer kafkaProducer;

    public AppointmentController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    public String createAppointment(@RequestBody AppointmentRequest request) {
        kafkaProducer.send(request);
        return "Appointment sent to Kafka";
    }
}
