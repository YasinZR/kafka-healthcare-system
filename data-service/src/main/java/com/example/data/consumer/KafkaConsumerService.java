package com.example.data.consumer;

import com.example.data.dto.AppointmentRequest;
import com.example.data.entity.Appointment;
import com.example.data.entity.Patient;
import com.example.data.repository.AppointmentRepository;
import com.example.data.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerService(PatientRepository patientRepo, AppointmentRepository appointmentRepo) {
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
    }

    @KafkaListener(topics = "appointments", groupId = "data-service-group")
    public void consume(String message) {
        try {
            AppointmentRequest dto = objectMapper.readValue(message, AppointmentRequest.class);

            Patient patient = patientRepo.findByName(dto.getPatientName())
                    .orElseGet(() -> {
                        Patient p = new Patient();
                        p.setName(dto.getPatientName());
                        return patientRepo.save(p);
                    });

            Appointment appointment = new Appointment();
            appointment.setDate(dto.getDate());
            appointment.setTime(dto.getTime());
            appointment.setDoctorName(dto.getDoctorName());
            appointment.setPatient(patient);

            appointmentRepo.save(appointment);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
