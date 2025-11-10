package com.example.healthsphere.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.healthsphere.Entities.Appointment;
import com.example.healthsphere.Entities.Appointment.AppointmentStatus;
import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByStatus(AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId AND (:status IS NULL OR a.status = :status)")
    List<Appointment> findByPatientIdAndStatus(@Param("patientId") Long patientId, @Param("status") AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND (:status IS NULL OR a.status = :status)")
    List<Appointment> findByDoctorIdAndStatus(@Param("doctorId") Long doctorId, @Param("status") AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
            "AND ((a.appointmentDate > :startDate OR (a.appointmentDate = :startDate AND a.appointmentTime >= :startTime)) " +
            "AND (a.appointmentDate < :endDate OR (a.appointmentDate = :endDate AND a.appointmentTime <= :endTime)))")
    List<Appointment> findByDoctorIdAndDateRange(@Param("doctorId") Long doctorId,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("startTime") LocalTime startTime,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("endTime") LocalTime endTime);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId " +
            "AND ((a.appointmentDate > :startDate OR (a.appointmentDate = :startDate AND a.appointmentTime >= :startTime)) " +
            "AND (a.appointmentDate < :endDate OR (a.appointmentDate = :endDate AND a.appointmentTime <= :endTime)))")
    List<Appointment> findByPatientIdAndDateRange(@Param("patientId") Long patientId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("startTime") LocalTime startTime,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("endTime") LocalTime endTime);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId AND a.status IN ('CONFIRMED', 'REQUESTED')")
    Long countActiveAppointmentsByDoctor(@Param("doctorId") Long doctorId);
}
