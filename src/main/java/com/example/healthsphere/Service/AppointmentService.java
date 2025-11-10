package com.example.healthsphere.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.Appointment;
import com.example.healthsphere.Entities.Appointment.AppointmentStatus;

public interface AppointmentService {
    Appointment bookAppointment(Appointment appointment);
    Appointment updateAppointment(Long id, Appointment appointment);
    Optional<Appointment> getAppointmentById(Long id);
    List<Appointment> getAllAppointments();
    List<Appointment> getAppointmentsByPatientId(Long patientId);
    List<Appointment> getAppointmentsByDoctorId(Long doctorId);
    List<Appointment> getAppointmentsByStatus(AppointmentStatus status);
    List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> getTodayAppointmentsByDoctor(Long doctorId);
    List<Appointment> getUpcomingAppointmentsByPatient(Long patientId);
    Appointment confirmAppointment(Long id);
    Appointment rescheduleAppointment(Long id, LocalDateTime newDateTime);
    Appointment cancelAppointment(Long id, String reason);
    Appointment completeAppointment(Long id, String notes);
    void deleteAppointment(Long id);
    List<Appointment> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
    boolean isSlotAvailable(Long doctorId, LocalDateTime dateTime);
}