package com.example.healthsphere.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.EHR;
import com.example.healthsphere.Entities.Patient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EHRRepository extends JpaRepository<EHR, Long> {
    List<EHR> findByPatient(Patient patient);
    List<EHR> findByPatientId(Long patientId);
    
    @Query("SELECT e FROM EHR e WHERE e.patient.id = :patientId ORDER BY e.createdAt DESC")
    List<EHR> findByPatientIdOrderByCreatedAtDesc(@Param("patientId") Long patientId);
    
    @Query("SELECT e FROM EHR e WHERE e.patient.id = :patientId AND e.createdAt BETWEEN :startDate AND :endDate")
    List<EHR> findByPatientIdAndDateRange(@Param("patientId") Long patientId, 
                                           @Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
    
    Optional<EHR> findTopByPatientIdOrderByCreatedAtDesc(Long patientId);
}
