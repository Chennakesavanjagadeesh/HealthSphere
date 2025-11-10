package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.VerificationStatus;
import com.example.healthsphere.Service.DoctorService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@Valid @RequestBody Doctor doctor) {
        Doctor registeredDoctor = doctorService.registerDoctor(doctor);
        return new ResponseEntity<>(registeredDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Doctor> getDoctorByEmail(@PathVariable String email) {
        return doctorService.getDoctorByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialty(@PathVariable String specialty) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/verified")
    public ResponseEntity<List<Doctor>> getVerifiedDoctors() {
        List<Doctor> doctors = doctorService.getVerifiedDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/verification-status/{status}")
    public ResponseEntity<List<Doctor>> getDoctorsByVerificationStatus(@PathVariable VerificationStatus status) {
        List<Doctor> doctors = doctorService.getDoctorsByVerificationStatus(status);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(@RequestParam String searchTerm) {
        List<Doctor> doctors = doctorService.searchDoctors(searchTerm);
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Doctor> verifyDoctor(@PathVariable Long id) {
        Doctor doctor = doctorService.verifyDoctor(id);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Doctor> rejectDoctor(@PathVariable Long id, @RequestParam String reason) {
        Doctor doctor = doctorService.rejectDoctor(id, reason);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{id}/telemedicine")
    public ResponseEntity<Doctor> updateTelemedicine(@PathVariable Long id, @RequestParam Boolean enabled) {
        Doctor doctor = doctorService.updateAvailability(id, enabled);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}