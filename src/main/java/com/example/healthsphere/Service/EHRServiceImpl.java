package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.EHR;
import com.example.healthsphere.Repositories.EHRRepository;
import com.example.healthsphere.Repositories.PatientRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EHRServiceImpl implements EHRService {

    @Autowired
    private EHRRepository ehrRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public EHR createEHR(EHR ehr) {
        if (ehr.getPatient() == null || !patientRepository.existsById(ehr.getPatient().getId())) {
            throw new RuntimeException("Patient not found");
        }
        return ehrRepository.save(ehr);
    }

    @Override
    public EHR updateEHR(Long id, EHR ehr) {
        EHR existingEHR = ehrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + id));
        
        existingEHR.setDiagnoses(ehr.getDiagnoses());
        existingEHR.setMedications(ehr.getMedications());
        existingEHR.setAllergies(ehr.getAllergies());
        existingEHR.setLabResults(ehr.getLabResults());
        existingEHR.setImmunizations(ehr.getImmunizations());
        existingEHR.setVisitNotes(ehr.getVisitNotes());
        existingEHR.setVitalSigns(ehr.getVitalSigns());
        
        return ehrRepository.save(existingEHR);
    }

    @Override
    public Optional<EHR> getEHRById(Long id) {
        return ehrRepository.findById(id);
    }

    @Override
    public List<EHR> getEHRsByPatientId(Long patientId) {
        return ehrRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    @Override
    public Optional<EHR> getLatestEHRByPatientId(Long patientId) {
        return ehrRepository.findTopByPatientIdOrderByCreatedAtDesc(patientId);
    }

    @Override
    public List<EHR> getEHRsByPatientIdAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return ehrRepository.findByPatientIdAndDateRange(patientId, startDate, endDate);
    }

    @Override
    public void deleteEHR(Long id) {
        if (!ehrRepository.existsById(id)) {
            throw new RuntimeException("EHR not found with id: " + id);
        }
        ehrRepository.deleteById(id);
    }

    @Override
    public EHR addDiagnosis(Long ehrId, String diagnosis) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        String currentDiagnoses = ehr.getDiagnoses() != null ? ehr.getDiagnoses() : "";
        ehr.setDiagnoses(currentDiagnoses.isEmpty() ? diagnosis : currentDiagnoses + "\n" + diagnosis);
        
        return ehrRepository.save(ehr);
    }

    @Override
    public EHR addMedication(Long ehrId, String medication) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        String currentMedications = ehr.getMedications() != null ? ehr.getMedications() : "";
        ehr.setMedications(currentMedications.isEmpty() ? medication : currentMedications + "\n" + medication);
        
        return ehrRepository.save(ehr);
    }

    @Override
    public EHR addAllergy(Long ehrId, String allergy) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        String currentAllergies = ehr.getAllergies() != null ? ehr.getAllergies() : "";
        ehr.setAllergies(currentAllergies.isEmpty() ? allergy : currentAllergies + "\n" + allergy);
        
        return ehrRepository.save(ehr);
    }

    @Override
    public EHR addLabResult(Long ehrId, String labResult) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        String currentLabResults = ehr.getLabResults() != null ? ehr.getLabResults() : "";
        ehr.setLabResults(currentLabResults.isEmpty() ? labResult : currentLabResults + "\n" + labResult);
        
        return ehrRepository.save(ehr);
    }

    @Override
    public EHR addImmunization(Long ehrId, String immunization) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        String currentImmunizations = ehr.getImmunizations() != null ? ehr.getImmunizations() : "";
        ehr.setImmunizations(currentImmunizations.isEmpty() ? immunization : currentImmunizations + "\n" + immunization);
        
        return ehrRepository.save(ehr);
    }

    @Override
    public EHR addVisitNote(Long ehrId, String visitNote) {
        EHR ehr = ehrRepository.findById(ehrId)
                .orElseThrow(() -> new RuntimeException("EHR not found with id: " + ehrId));
        
        String currentVisitNotes = ehr.getVisitNotes() != null ? ehr.getVisitNotes() : "";
        ehr.setVisitNotes(currentVisitNotes.isEmpty() ? visitNote : currentVisitNotes + "\n" + visitNote);
        
        return ehrRepository.save(ehr);
    }
}