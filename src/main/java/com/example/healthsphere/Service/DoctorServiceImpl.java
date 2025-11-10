package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Doctor;
import com.example.healthsphere.Entities.UserStatus;
import com.example.healthsphere.Entities.VerificationStatus;
import com.example.healthsphere.Repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor registerDoctor(Doctor doctor) {
        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new RuntimeException("Doctor with email " + doctor.getEmail() + " already exists");
        }
        doctor.setStatus(UserStatus.PENDING_VERIFICATION);
        doctor.setVerificationStatus(VerificationStatus.PENDING);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        existingDoctor.setFirstName(doctor.getFirstName());
        existingDoctor.setLastName(doctor.getLastName());
        existingDoctor.setPhoneNumber(doctor.getPhoneNumber());
        existingDoctor.setStreetAddress(doctor.getStreetAddress());
        existingDoctor.setCity(doctor.getCity());
        existingDoctor.setState(doctor.getState());
        existingDoctor.setZipCode(doctor.getZipCode());
        existingDoctor.setCountry(doctor.getCountry());
        existingDoctor.setSpecialty(doctor.getSpecialty());
        existingDoctor.setQualification(doctor.getQualification());
        existingDoctor.setExperienceYears(doctor.getExperienceYears());
        existingDoctor.setConsultationFee(doctor.getConsultationFee());
        existingDoctor.setBio(doctor.getBio());
        
        return doctorRepository.save(existingDoctor);
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    @Override
    public List<Doctor> getDoctorsByVerificationStatus(VerificationStatus status) {
        return doctorRepository.findByVerificationStatus(status);
    }

    @Override
    public List<Doctor> getVerifiedDoctors() {
        return doctorRepository.findByVerificationStatus(VerificationStatus.VERIFIED);
    }

    @Override
    public List<Doctor> searchDoctors(String searchTerm) {
        return doctorRepository.searchDoctors(searchTerm);
    }

    @Override
    public Doctor verifyDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        doctor.setVerificationStatus(VerificationStatus.VERIFIED);
        doctor.setStatus(UserStatus.ACTIVE);
        
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor rejectDoctor(Long id, String reason) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        doctor.setVerificationStatus(VerificationStatus.REJECTED);
        doctor.setStatus(UserStatus.INACTIVE);
        
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public Doctor updateAvailability(Long id, Boolean telemedicineEnabled) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        doctor.setTelemedicineEnabled(telemedicineEnabled);
        
        return doctorRepository.save(doctor);
    }
}