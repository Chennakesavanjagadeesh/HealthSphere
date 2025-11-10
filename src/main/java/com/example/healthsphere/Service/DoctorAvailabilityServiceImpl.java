package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.DoctorAvailability;
import com.example.healthsphere.Repositories.DoctorAvailabilityRepository;
import com.example.healthsphere.Repositories.DoctorRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    @Autowired
    private DoctorAvailabilityRepository availabilityRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorAvailability createAvailability(DoctorAvailability availability) {
        if (!doctorRepository.existsById(availability.getDoctor().getId())) {
            throw new RuntimeException("Doctor not found");
        }
        return availabilityRepository.save(availability);
    }

    @Override
    public DoctorAvailability updateAvailability(Long id, DoctorAvailability availability) {
        DoctorAvailability existing = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));
        
        existing.setDayOfWeek(availability.getDayOfWeek());
        existing.setStartTime(availability.getStartTime());
        existing.setEndTime(availability.getEndTime());
        existing.setIsAvailable(availability.getIsAvailable());
        existing.setSlotDurationMinutes(availability.getSlotDurationMinutes());
        existing.setBreakStartTime(availability.getBreakStartTime());
        existing.setBreakEndTime(availability.getBreakEndTime());
        existing.setEffectiveDate(availability.getEffectiveDate());
        existing.setEndDate(availability.getEndDate());
        
        return availabilityRepository.save(existing);
    }

    @Override
    public Optional<DoctorAvailability> getAvailabilityById(Long id) {
        return availabilityRepository.findById(id);
    }

    @Override
    public List<DoctorAvailability> getAvailabilitiesByDoctorId(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<DoctorAvailability> getAvailableSlotsByDoctor(Long doctorId) {
        return availabilityRepository.findAvailableSlotsByDoctor(doctorId);
    }

    @Override
    public List<DoctorAvailability> getAvailabilityForDate(Long doctorId, LocalDate date) {
        return availabilityRepository.findActiveAvailabilityForDate(doctorId, date);
    }

    @Override
    public void deleteAvailability(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new RuntimeException("Availability not found with id: " + id);
        }
        availabilityRepository.deleteById(id);
    }

    @Override
    public DoctorAvailability markUnavailable(Long id) {
        DoctorAvailability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));
        
        availability.setIsAvailable(false);
        return availabilityRepository.save(availability);
    }

    @Override
    public DoctorAvailability markAvailable(Long id) {
        DoctorAvailability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));
        
        availability.setIsAvailable(true);
        return availabilityRepository.save(availability);
    }

    @Override
    public List<LocalTime> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        List<LocalTime> slots = new ArrayList<>();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        
        List<DoctorAvailability> availabilities = availabilityRepository
                .findActiveAvailabilityForDate(doctorId, date);
        
        for (DoctorAvailability availability : availabilities) {
            if (availability.getDayOfWeek() == dayOfWeek && availability.getIsAvailable()) {
                LocalTime current = availability.getStartTime();
                LocalTime end = availability.getEndTime();
                int slotDuration = availability.getSlotDurationMinutes();
                
                while (current.plusMinutes(slotDuration).isBefore(end) || 
                       current.plusMinutes(slotDuration).equals(end)) {
                    
                    // Skip break time
                    if (availability.getBreakStartTime() != null && 
                        availability.getBreakEndTime() != null) {
                        if (current.isBefore(availability.getBreakStartTime()) || 
                            current.isAfter(availability.getBreakEndTime()) ||
                            current.equals(availability.getBreakEndTime())) {
                            slots.add(current);
                        }
                    } else {
                        slots.add(current);
                    }
                    
                    current = current.plusMinutes(slotDuration);
                }
            }
        }
        
        return slots;
    }
}