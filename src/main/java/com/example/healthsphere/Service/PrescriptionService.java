package com.example.healthsphere.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.Prescription;
import com.example.healthsphere.Entities.PrescriptionStatus;

public interface PrescriptionService {
    Prescription createPrescription(Prescription prescription);
    Prescription updatePrescription(Long id, Prescription prescription);
    Optional<Prescription> getPrescriptionById(Long id);
    List<Prescription> getAllPrescriptions();
    List<Prescription> getPrescriptionsByPatientId(Long patientId);
    List<Prescription> getPrescriptionsByDoctorId(Long doctorId);
    List<Prescription> getPrescriptionsByStatus(PrescriptionStatus status);
    List<Prescription> getPrescriptionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Prescription updatePrescriptionStatus(Long id, PrescriptionStatus status);
    Prescription sendToPharmacy(Long id, String pharmacyName);
    void deletePrescription(Long id);
}
