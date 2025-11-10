package com.example.healthsphere.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.EHR;

public interface EHRService {
    EHR createEHR(EHR ehr);
    EHR updateEHR(Long id, EHR ehr);
    Optional<EHR> getEHRById(Long id);
    List<EHR> getEHRsByPatientId(Long patientId);
    Optional<EHR> getLatestEHRByPatientId(Long patientId);
    List<EHR> getEHRsByPatientIdAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate);
    void deleteEHR(Long id);
    EHR addDiagnosis(Long ehrId, String diagnosis);
    EHR addMedication(Long ehrId, String medication);
    EHR addAllergy(Long ehrId, String allergy);
    EHR addLabResult(Long ehrId, String labResult);
    EHR addImmunization(Long ehrId, String immunization);
    EHR addVisitNote(Long ehrId, String visitNote);
}