package com.example.healthsphere.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.healthsphere.Entities.Billing;
import com.example.healthsphere.Entities.BillingStatus;
import com.example.healthsphere.Entities.PaymentMethod;

public interface BillingService {
    Billing createBilling(Billing billing);
    Billing updateBilling(Long id, Billing billing);
    Optional<Billing> getBillingById(Long id);
    Optional<Billing> getBillingByInvoiceNumber(String invoiceNumber);
    List<Billing> getAllBillings();
    List<Billing> getBillingsByPatientId(Long patientId);
    List<Billing> getBillingsByStatus(BillingStatus status);
    List<Billing> getBillingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Billing> getOverdueBills();
    Billing markAsPaid(Long id, PaymentMethod paymentMethod, String transactionId);
    Billing markAsOverdue(Long id);
    Billing processBillingForAppointment(Long appointmentId);
    BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);
    void deleteBilling(Long id);
}