package com.example.healthsphere.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.VerificationStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	boolean existsByEmail(String email);
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByMedicalLicenseNumber(String medicalLicenseNumber);
    List<Doctor> findBySpecialty(String specialty);
    List<Doctor> findByVerificationStatus(VerificationStatus status);
    List<Doctor> findByTelemedicineEnabled(Boolean enabled);
    
    @Query("SELECT d FROM Doctor d WHERE d.verificationStatus = 'VERIFIED' AND d.specialty = :specialty")
    List<Doctor> findVerifiedDoctorsBySpecialty(@Param("specialty") String specialty);
    
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(d.specialty) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Doctor> searchDoctors(@Param("searchTerm") String searchTerm);
}
