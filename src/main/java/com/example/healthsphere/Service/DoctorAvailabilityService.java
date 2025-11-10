package com.example.healthsphere.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.DoctorAvailability;

public interface DoctorAvailabilityService {
    DoctorAvailability createAvailability(DoctorAvailability availability);
    DoctorAvailability updateAvailability(Long id, DoctorAvailability availability);
    Optional<DoctorAvailability> getAvailabilityById(Long id);
    List<DoctorAvailability> getAvailabilitiesByDoctorId(Long doctorId);
    List<DoctorAvailability> getAvailableSlotsByDoctor(Long doctorId);
    List<DoctorAvailability> getAvailabilityForDate(Long doctorId, LocalDate date);
    void deleteAvailability(Long id);
    DoctorAvailability markUnavailable(Long id);
    DoctorAvailability markAvailable(Long id);
    List<LocalTime> getAvailableTimeSlots(Long doctorId, LocalDate date);
}