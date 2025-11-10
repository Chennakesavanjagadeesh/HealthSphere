package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthsphere.Entities.Gender;
import com.example.healthsphere.Entities.Patient;
import com.example.healthsphere.Service.PatientService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody Patient patient) {
        Patient registeredPatient = patientService.registerPatient(patient);
        return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Patient>> getPatientsByGender(@PathVariable Gender gender) {
        List<Patient> patients = patientService.getPatientsByGender(gender);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/insurance/{provider}")
    public ResponseEntity<List<Patient>> getPatientsByInsurance(@PathVariable String provider) {
        List<Patient> patients = patientService.getPatientsByInsuranceProvider(provider);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String searchTerm) {
        List<Patient> patients = patientService.searchPatients(searchTerm);
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}/emergency-contact")
    public ResponseEntity<Patient> updateEmergencyContact(@PathVariable Long id,
                                                          @RequestParam String name,
                                                          @RequestParam String phone,
                                                          @RequestParam String relationship) {
        Patient patient = patientService.updateEmergencyContact(id, name, phone, relationship);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}/insurance")
    public ResponseEntity<Patient> updateInsuranceInfo(@PathVariable Long id,
                                                       @RequestParam String provider,
                                                       @RequestParam String policyNumber) {
        Patient patient = patientService.updateInsuranceInfo(id, provider, policyNumber);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
