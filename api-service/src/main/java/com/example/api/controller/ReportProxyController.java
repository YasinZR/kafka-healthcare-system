package com.example.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportProxyController {

    private final RestTemplate restTemplate;

    public ReportProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String DATA_SERVICE_BASE_URL = "http://data-service:8081/api";

    @GetMapping("/appointments")
    public List<?> getAllAppointments() {
        return restTemplate.getForObject(DATA_SERVICE_BASE_URL + "/appointments/search", List.class);
    }

    @GetMapping("/popular-doctors")
    public List<?> getPopularDoctors() {
        return restTemplate.getForObject(DATA_SERVICE_BASE_URL + "/reports/popular-doctors", List.class);
    }

    @GetMapping("/appointments-by-day")
    public List<?> getAppointmentsByDay() {
        return restTemplate.getForObject(DATA_SERVICE_BASE_URL + "/reports/appointments-by-day", List.class);
    }
}
