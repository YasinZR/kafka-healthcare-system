package com.example.data.controller;

import com.example.data.dto.AppointmentDto;
import com.example.data.entity.Appointment;
import com.example.data.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final AppointmentRepository appointmentRepository;

    public ReportController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/appointments/search")
    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(appointment -> {
            AppointmentDto dto = new AppointmentDto();
            dto.setPatientName(appointment.getPatient().getName());
            dto.setDoctorName(appointment.getDoctorName());
            dto.setDate(appointment.getDate());
            dto.setTime(appointment.getTime());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/reports/popular-doctors")
    public List<Map<String, Object>> getTopDoctors() {
        List<Appointment> all = appointmentRepository.findAll();

        Map<String, Long> countByDoctor = all.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctorName, Collectors.counting()));

        return countByDoctor.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("doctorName", entry.getKey());
                    result.put("appointmentsCount", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/reports/appointments-by-day")
    public List<Map<String, Object>> getAppointmentsByDay() {
        return appointmentRepository.findAll().stream()
                .collect(Collectors.groupingBy(Appointment::getDate, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("date", entry.getKey());
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }
}
