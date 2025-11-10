package com.example.healthsphere.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.Patient;
import com.example.healthsphere.Entities.Prescription;
import com.example.healthsphere.Entities.PrescriptionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient(Patient patient);
    List<Prescription> findByDoctor(Doctor doctor);
    List<Prescription> findByStatus(PrescriptionStatus status);
    
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId ORDER BY p.createdAt DESC")
    List<Prescription> findByPatientIdOrderByCreatedAtDesc(@Param("patientId") Long patientId);
    
    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorId ORDER BY p.createdAt DESC")
    List<Prescription> findByDoctorIdOrderByCreatedAtDesc(@Param("doctorId") Long doctorId);
    
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId AND p.status = :status")
    List<Prescription> findByPatientIdAndStatus(@Param("patientId") Long patientId, @Param("status") PrescriptionStatus status);
    
    @Query("SELECT p FROM Prescription p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<Prescription> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
