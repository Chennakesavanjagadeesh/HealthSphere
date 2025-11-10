package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthsphere.Entities.EHR;
import com.example.healthsphere.Service.EHRService;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ehr")
@CrossOrigin(origins = "*")
public class EHRController {

    @Autowired
    private EHRService ehrService;

    @PostMapping
    public ResponseEntity<EHR> createEHR(@Valid @RequestBody EHR ehr) {
        EHR createdEHR = ehrService.createEHR(ehr);
        return new ResponseEntity<>(createdEHR, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EHR> updateEHR(@PathVariable Long id, @Valid @RequestBody EHR ehr) {
        EHR updatedEHR = ehrService.updateEHR(id, ehr);
        return ResponseEntity.ok(updatedEHR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EHR> getEHRById(@PathVariable Long id) {
        return ehrService.getEHRById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<EHR>> getEHRsByPatientId(@PathVariable Long patientId) {
        List<EHR> ehrs = ehrService.getEHRsByPatientId(patientId);
        return ResponseEntity.ok(ehrs);
    }

    @GetMapping("/patient/{patientId}/latest")
    public ResponseEntity<EHR> getLatestEHR(@PathVariable Long patientId) {
        return ehrService.getLatestEHRByPatientId(patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}/date-range")
    public ResponseEntity<List<EHR>> getEHRsByDateRange(@PathVariable Long patientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<EHR> ehrs = ehrService.getEHRsByPatientIdAndDateRange(patientId, startDate, endDate);
        return ResponseEntity.ok(ehrs);
    }

    @PutMapping("/{id}/diagnosis")
    public ResponseEntity<EHR> addDiagnosis(@PathVariable Long id, @RequestParam String diagnosis) {
        EHR ehr = ehrService.addDiagnosis(id, diagnosis);
        return ResponseEntity.ok(ehr);
    }

    @PutMapping("/{id}/medication")
    public ResponseEntity<EHR> addMedication(@PathVariable Long id, @RequestParam String medication) {
        EHR ehr = ehrService.addMedication(id, medication);
        return ResponseEntity.ok(ehr);
    }

    @PutMapping("/{id}/allergy")
    public ResponseEntity<EHR> addAllergy(@PathVariable Long id, @RequestParam String allergy) {
        EHR ehr = ehrService.addAllergy(id, allergy);
        return ResponseEntity.ok(ehr);
    }

    @PutMapping("/{id}/lab-result")
    public ResponseEntity<EHR> addLabResult(@PathVariable Long id, @RequestParam String labResult) {
        EHR ehr = ehrService.addLabResult(id, labResult);
        return ResponseEntity.ok(ehr);
    }

    @PutMapping("/{id}/immunization")
    public ResponseEntity<EHR> addImmunization(@PathVariable Long id, @RequestParam String immunization) {
        EHR ehr = ehrService.addImmunization(id, immunization);
        return ResponseEntity.ok(ehr);
    }

    @PutMapping("/{id}/visit-note")
    public ResponseEntity<EHR> addVisitNote(@PathVariable Long id, @RequestParam String visitNote) {
        EHR ehr = ehrService.addVisitNote(id, visitNote);
        return ResponseEntity.ok(ehr);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEHR(@PathVariable Long id) {
        ehrService.deleteEHR(id);
        return ResponseEntity.noContent().build();
    }
}
