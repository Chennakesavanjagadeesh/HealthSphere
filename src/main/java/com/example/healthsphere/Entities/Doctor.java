package com.example.healthsphere.Entities;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
public class Doctor extends User {
    
    @Column(name = "medical_license_number", unique = true)
    private String medicalLicenseNumber;
    
    @Column(name = "specialty")
    private String specialty;
    
    @Column(name = "qualification")
    private String qualification;
    
    @Column(name = "experience_years")
    private Integer experienceYears;
    
    @Column(name = "consultation_fee")
    private Double consultationFee;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
    
    @Column(name = "clinic_start_time")
    private LocalTime clinicStartTime;
    
    @Column(name = "clinic_end_time")
    private LocalTime clinicEndTime;
    
    @Column(name = "telemedicine_enabled")
    private Boolean telemedicineEnabled = false;
    
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DoctorAvailability> availabilities;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;
    
    public Doctor() {
        super();
        setRole(UserRole.DOCTOR);
    }
    
    // Getters and Setters
    public String getMedicalLicenseNumber() { return medicalLicenseNumber; }
    public void setMedicalLicenseNumber(String medicalLicenseNumber) { this.medicalLicenseNumber = medicalLicenseNumber; }
    
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    
    public Double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(Double consultationFee) { this.consultationFee = consultationFee; }
    
    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(VerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; }
    
    public LocalTime getClinicStartTime() { return clinicStartTime; }
    public void setClinicStartTime(LocalTime clinicStartTime) { this.clinicStartTime = clinicStartTime; }
    
    public LocalTime getClinicEndTime() { return clinicEndTime; }
    public void setClinicEndTime(LocalTime clinicEndTime) { this.clinicEndTime = clinicEndTime; }
    
    public Boolean getTelemedicineEnabled() { return telemedicineEnabled; }
    public void setTelemedicineEnabled(Boolean telemedicineEnabled) { this.telemedicineEnabled = telemedicineEnabled; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
    
    public List<DoctorAvailability> getAvailabilities() { return availabilities; }
    public void setAvailabilities(List<DoctorAvailability> availabilities) { this.availabilities = availabilities; }
    
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }
}
