package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Prescription;
import com.example.healthsphere.Entities.PrescriptionStatus;
import com.example.healthsphere.Repositories.PrescriptionRepository;
import com.example.healthsphere.Repositories.PatientRepository;
import com.example.healthsphere.Repositories.DoctorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Prescription createPrescription(Prescription prescription) {
        if (!patientRepository.existsById(prescription.getPatient().getId())) {
            throw new RuntimeException("Patient not found");
        }
        if (!doctorRepository.existsById(prescription.getDoctor().getId())) {
            throw new RuntimeException("Doctor not found");
        }
        prescription.setStatus(PrescriptionStatus.PRESCRIBED);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription updatePrescription(Long id, Prescription prescription) {
        Prescription existingPrescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        
        existingPrescription.setMedicationName(prescription.getMedicationName());
        existingPrescription.setDosage(prescription.getDosage());
        existingPrescription.setFrequency(prescription.getFrequency());
        existingPrescription.setDuration(prescription.getDuration());
        existingPrescription.setInstructions(prescription.getInstructions());
        
        return prescriptionRepository.save(existingPrescription);
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctorId(Long doctorId) {
        return prescriptionRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
    }

    @Override
    public List<Prescription> getPrescriptionsByStatus(PrescriptionStatus status) {
        return prescriptionRepository.findByStatus(status);
    }

    @Override
    public List<Prescription> getPrescriptionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return prescriptionRepository.findByDateRange(startDate, endDate);
    }

    @Override
    public Prescription updatePrescriptionStatus(Long id, PrescriptionStatus status) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        
        prescription.setStatus(status);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription sendToPharmacy(Long id, String pharmacyName) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        
        prescription.setPharmacyName(pharmacyName);
        prescription.setStatus(PrescriptionStatus.SENT_TO_PHARMACY);
        
        return prescriptionRepository.save(prescription);
    }

    @Override
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found with id: " + id);
        }
        prescriptionRepository.deleteById(id);
    }
}