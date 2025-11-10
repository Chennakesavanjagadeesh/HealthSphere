package com.example.healthsphere.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.DoctorAvailability;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
    List<DoctorAvailability> findByDoctor(Doctor doctor);
    List<DoctorAvailability> findByDoctorId(Long doctorId);
    List<DoctorAvailability> findByDayOfWeek(DayOfWeek dayOfWeek);
    
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId AND da.isAvailable = true")
    List<DoctorAvailability> findAvailableSlotsByDoctor(@Param("doctorId") Long doctorId);
    
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId AND da.dayOfWeek = :dayOfWeek")
    List<DoctorAvailability> findByDoctorIdAndDayOfWeek(@Param("doctorId") Long doctorId, @Param("dayOfWeek") DayOfWeek dayOfWeek);
    
    @Query("SELECT da FROM DoctorAvailability da WHERE da.doctor.id = :doctorId " +
           "AND (da.effectiveDate IS NULL OR da.effectiveDate <= :date) " +
           "AND (da.endDate IS NULL OR da.endDate >= :date)")
    List<DoctorAvailability> findActiveAvailabilityForDate(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}