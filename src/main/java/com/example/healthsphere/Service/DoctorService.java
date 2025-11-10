package com.example.healthsphere.Service;

import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.VerificationStatus;

public interface DoctorService {
    Doctor registerDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor doctor);
    Optional<Doctor> getDoctorById(Long id);
    Optional<Doctor> getDoctorByEmail(String email);
    List<Doctor> getAllDoctors();
    List<Doctor> getDoctorsBySpecialty(String specialty);
    List<Doctor> getDoctorsByVerificationStatus(VerificationStatus status);
    List<Doctor> getVerifiedDoctors();
    List<Doctor> searchDoctors(String searchTerm);
    Doctor verifyDoctor(Long id);
    Doctor rejectDoctor(Long id, String reason);
    void deleteDoctor(Long id);
    Doctor updateAvailability(Long id, Boolean telemedicineEnabled);
}