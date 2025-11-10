package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthsphere.Entities.DoctorAvailability;
import com.example.healthsphere.Service.DoctorAvailabilityService;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-availability")
@CrossOrigin(origins = "*")
public class DoctorAvailabilityController {

    @Autowired
    private DoctorAvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<DoctorAvailability> createAvailability(@Valid @RequestBody DoctorAvailability availability) {
        DoctorAvailability createdAvailability = availabilityService.createAvailability(availability);
        return new ResponseEntity<>(createdAvailability, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorAvailability> updateAvailability(@PathVariable Long id, @Valid @RequestBody DoctorAvailability availability) {
        DoctorAvailability updatedAvailability = availabilityService.updateAvailability(id, availability);
        return ResponseEntity.ok(updatedAvailability);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorAvailability> getAvailabilityById(@PathVariable Long id) {
        return availabilityService.getAvailabilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAvailability>> getAvailabilitiesByDoctorId(@PathVariable Long doctorId) {
        List<DoctorAvailability> availabilities = availabilityService.getAvailabilitiesByDoctorId(doctorId);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("/doctor/{doctorId}/available")
    public ResponseEntity<List<DoctorAvailability>> getAvailableSlotsByDoctor(@PathVariable Long doctorId) {
        List<DoctorAvailability> availabilities = availabilityService.getAvailableSlotsByDoctor(doctorId);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<List<DoctorAvailability>> getAvailabilityForDate(@PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<DoctorAvailability> availabilities = availabilityService.getAvailabilityForDate(doctorId, date);
        return ResponseEntity.ok(availabilities);
    }

    @GetMapping("/doctor/{doctorId}/time-slots/{date}")
    public ResponseEntity<List<LocalTime>> getAvailableTimeSlots(@PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LocalTime> timeSlots = availabilityService.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(timeSlots);
    }

    @PutMapping("/{id}/mark-unavailable")
    public ResponseEntity<DoctorAvailability> markUnavailable(@PathVariable Long id) {
        DoctorAvailability availability = availabilityService.markUnavailable(id);
        return ResponseEntity.ok(availability);
    }

    @PutMapping("/{id}/mark-available")
    public ResponseEntity<DoctorAvailability> markAvailable(@PathVariable Long id) {
        DoctorAvailability availability = availabilityService.markAvailable(id);
        return ResponseEntity.ok(availability);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}