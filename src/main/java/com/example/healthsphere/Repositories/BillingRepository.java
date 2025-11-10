package com.example.healthsphere.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.Billing;
import com.example.healthsphere.Entities.BillingStatus;
import com.example.healthsphere.Entities.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByInvoiceNumber(String invoiceNumber);
    List<Billing> findByPatient(Patient patient);
    List<Billing> findByStatus(BillingStatus status);
    
    @Query("SELECT b FROM Billing b WHERE b.patient.id = :patientId ORDER BY b.createdAt DESC")
    List<Billing> findByPatientIdOrderByCreatedAtDesc(@Param("patientId") Long patientId);
    
    @Query("SELECT b FROM Billing b WHERE b.createdAt BETWEEN :startDate AND :endDate")
    List<Billing> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(b.amount) FROM Billing b WHERE b.status = 'PAID' AND b.paidAt BETWEEN :startDate AND :endDate")
    BigDecimal getTotalRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT b FROM Billing b WHERE b.status = 'OVERDUE' AND b.dueDate < :currentDate")
    List<Billing> findOverdueBills(@Param("currentDate") LocalDateTime currentDate);
}