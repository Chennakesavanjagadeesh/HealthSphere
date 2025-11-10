package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Gender;
import com.example.healthsphere.Entities.Patient;
import com.example.healthsphere.Entities.UserStatus;
import com.example.healthsphere.Repositories.PatientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient registerPatient(Patient patient) {
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new RuntimeException("Patient with email " + patient.getEmail() + " already exists");
        }
        patient.setStatus(UserStatus.ACTIVE);
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setPhoneNumber(patient.getPhoneNumber());
        existingPatient.setStreetAddress(patient.getStreetAddress());
        existingPatient.setCity(patient.getCity());
        existingPatient.setState(patient.getState());
        existingPatient.setZipCode(patient.getZipCode());
        existingPatient.setCountry(patient.getCountry());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());
        existingPatient.setGender(patient.getGender());
        existingPatient.setBloodGroup(patient.getBloodGroup());
        
        return patientRepository.save(existingPatient);
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> getPatientsByGender(Gender gender) {
        return patientRepository.findByGender(gender);
    }

    @Override
    public List<Patient> getPatientsByInsuranceProvider(String provider) {
        return patientRepository.findByInsuranceProvider(provider);
    }

    @Override
    public List<Patient> searchPatients(String searchTerm) {
        return patientRepository.searchPatients(searchTerm);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    public Patient updateEmergencyContact(Long id, String name, String phone, String relationship) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        patient.setEmergencyContactName(name);
        patient.setEmergencyContactPhone(phone);
        patient.setEmergencyContactRelationship(relationship);
        
        return patientRepository.save(patient);
    }

    @Override
    public Patient updateInsuranceInfo(Long id, String provider, String policyNumber) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        patient.setInsuranceProvider(provider);
        patient.setInsurancePolicyNumber(policyNumber);
        
        return patientRepository.save(patient);
    }
}