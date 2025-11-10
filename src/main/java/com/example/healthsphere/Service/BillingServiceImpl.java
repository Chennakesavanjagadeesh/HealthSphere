package com.example.healthsphere.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.healthsphere.Entities.Appointment;
import com.example.healthsphere.Entities.Billing;
import com.example.healthsphere.Entities.BillingStatus;
import com.example.healthsphere.Entities.PaymentMethod;
import com.example.healthsphere.Repositories.AppointmentRepository;
import com.example.healthsphere.Repositories.BillingRepository;
import com.example.healthsphere.Repositories.PatientRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingRepository billingRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Billing createBilling(Billing billing) {
        if (!patientRepository.existsById(billing.getPatient().getId())) {
            throw new RuntimeException("Patient not found");
        }
        
        // Generate unique invoice number if not provided
        if (billing.getInvoiceNumber() == null || billing.getInvoiceNumber().isEmpty()) {
            billing.setInvoiceNumber(generateInvoiceNumber());
        }
        
        billing.setStatus(BillingStatus.PENDING);
        
        // Set due date to 30 days from now if not provided
        if (billing.getDueDate() == null) {
            billing.setDueDate(LocalDateTime.now().plusDays(30));
        }
        
        return billingRepository.save(billing);
    }

    @Override
    public Billing updateBilling(Long id, Billing billing) {
        Billing existingBilling = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
        
        existingBilling.setAmount(billing.getAmount());
        existingBilling.setDescription(billing.getDescription());
        existingBilling.setDueDate(billing.getDueDate());
        
        return billingRepository.save(existingBilling);
    }

    @Override
    public Optional<Billing> getBillingById(Long id) {
        return billingRepository.findById(id);
    }

    @Override
    public Optional<Billing> getBillingByInvoiceNumber(String invoiceNumber) {
        return billingRepository.findByInvoiceNumber(invoiceNumber);
    }

    @Override
    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    @Override
    public List<Billing> getBillingsByPatientId(Long patientId) {
        return billingRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    @Override
    public List<Billing> getBillingsByStatus(BillingStatus status) {
        return billingRepository.findByStatus(status);
    }

    @Override
    public List<Billing> getBillingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return billingRepository.findByDateRange(startDate, endDate);
    }

    @Override
    public List<Billing> getOverdueBills() {
        return billingRepository.findOverdueBills(LocalDateTime.now());
    }

    @Override
    public Billing markAsPaid(Long id, PaymentMethod paymentMethod, String transactionId) {
        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
        
        billing.setStatus(BillingStatus.PAID);
        billing.setPaymentMethod(paymentMethod);
        billing.setTransactionId(transactionId);
        billing.setPaidAt(LocalDateTime.now());
        
        return billingRepository.save(billing);
    }

    @Override
    public Billing markAsOverdue(Long id) {
        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
        
        billing.setStatus(BillingStatus.OVERDUE);
        return billingRepository.save(billing);
    }

    @Override
    public Billing processBillingForAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        
        Billing billing = new Billing();
        billing.setPatient(appointment.getPatient());
        billing.setAppointment(appointment);
        billing.setInvoiceNumber(generateInvoiceNumber());
        
        // Get consultation fee from doctor
        BigDecimal amount = BigDecimal.valueOf(
            appointment.getDoctor().getConsultationFee() != null 
                ? appointment.getDoctor().getConsultationFee() 
                : 0.0
        );
        billing.setAmount(amount);
        
        billing.setDescription("Consultation Fee - Dr. " + 
            appointment.getDoctor().getFirstName() + " " + 
            appointment.getDoctor().getLastName());
        
        billing.setStatus(BillingStatus.PENDING);
        billing.setDueDate(LocalDateTime.now().plusDays(30));
        
        return billingRepository.save(billing);
    }

    @Override
    public BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal revenue = billingRepository.getTotalRevenue(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    @Override
    public void deleteBilling(Long id) {
        if (!billingRepository.existsById(id)) {
            throw new RuntimeException("Billing not found with id: " + id);
        }
        billingRepository.deleteById(id);
    }
    
    /**
     * Generates a unique invoice number based on timestamp
     * Format: INV-YYYYMMDDHHMMSS
     */
    private String generateInvoiceNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "INV-" + timestamp;
    }
}