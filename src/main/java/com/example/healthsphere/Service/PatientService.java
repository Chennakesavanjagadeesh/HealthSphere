package com.example.healthsphere.Service;



import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.Gender;
import com.example.healthsphere.Entities.Patient;

public interface PatientService {
    Patient registerPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    Optional<Patient> getPatientById(Long id);
    Optional<Patient> getPatientByEmail(String email);
    List<Patient> getAllPatients();
    List<Patient> getPatientsByGender(Gender gender);
    List<Patient> getPatientsByInsuranceProvider(String provider);
    List<Patient> searchPatients(String searchTerm);
    void deletePatient(Long id);
    Patient updateEmergencyContact(Long id, String name, String phone, String relationship);
    Patient updateInsuranceInfo(Long id, String provider, String policyNumber);
}

