package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Appointment;
import com.example.healthsphere.Entities.Appointment.AppointmentStatus;
import com.example.healthsphere.Repositories.AppointmentRepository;
import com.example.healthsphere.Repositories.DoctorRepository;
import com.example.healthsphere.Repositories.PatientRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Appointment bookAppointment(Appointment appointment) {
        if (!patientRepository.existsById(appointment.getPatient().getId())) {
            throw new RuntimeException("Patient not found");
        }
        if (!doctorRepository.existsById(appointment.getDoctor().getId())) {
            throw new RuntimeException("Doctor not found");
        }

        LocalDateTime slotDateTime = LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime());

        if (!isSlotAvailable(appointment.getDoctor().getId(), slotDateTime)) {
            throw new RuntimeException("This time slot is not available");
        }

        appointment.setStatus(AppointmentStatus.REQUESTED);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
        existingAppointment.setReason(appointment.getReason());
        existingAppointment.setType(appointment.getType());
        existingAppointment.setNotes(appointment.getNotes());

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, null);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, null);
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    @Override
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findAll().stream()
                .filter(a -> {
                    LocalDateTime dt = a.getAppointmentDateTime();
                    return !dt.isBefore(startDate) && !dt.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getTodayAppointmentsByDoctor(Long doctorId) {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByDoctorIdAndDateRange(doctorId, today, LocalTime.MIN, today, LocalTime.MAX);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByPatient(Long patientId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusMonths(3);
        return getAppointmentsByPatientId(patientId).stream()
                .filter(a -> a.getAppointmentDateTime().isAfter(now) &&
                        (a.getStatus() == AppointmentStatus.REQUESTED || a.getStatus() == AppointmentStatus.CONFIRMED))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment confirmAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment rescheduleAppointment(Long id, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        if (!isSlotAvailable(appointment.getDoctor().getId(), newDateTime)) {
            throw new RuntimeException("New time slot is not available");
        }

        appointment.setAppointmentDate(newDateTime.toLocalDate());
        appointment.setAppointmentTime(newDateTime.toLocalTime());
        appointment.setStatus(AppointmentStatus.RESCHEDULED);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment cancelAppointment(Long id, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setNotes(reason);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment completeAppointment(Long id, String notes) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setNotes(notes);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByDoctorIdAndDateRange(doctorId,
                startDate.toLocalDate(), startDate.toLocalTime(),
                endDate.toLocalDate(), endDate.toLocalTime());
    }

    @Override
    public boolean isSlotAvailable(Long doctorId, LocalDateTime dateTime) {
        LocalDateTime slotStart = dateTime.minusMinutes(15);
        LocalDateTime slotEnd = dateTime.plusMinutes(15);

        return getAppointmentsByDoctorAndDateRange(doctorId, slotStart, slotEnd).stream()
                .noneMatch(a -> a.getStatus() == AppointmentStatus.REQUESTED || a.getStatus() == AppointmentStatus.CONFIRMED);
    }
}
