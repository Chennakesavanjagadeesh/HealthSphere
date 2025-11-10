package com.example.healthsphere.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthsphere.Entities.Billing;
import com.example.healthsphere.Entities.BillingStatus;
import com.example.healthsphere.Entities.PaymentMethod;
import com.example.healthsphere.Service.BillingService;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<Billing> createBilling(@Valid @RequestBody Billing billing) {
        Billing createdBilling = billingService.createBilling(billing);
        return new ResponseEntity<>(createdBilling, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> updateBilling(@PathVariable Long id, @Valid @RequestBody Billing billing) {
        Billing updatedBilling = billingService.updateBilling(id, billing);
        return ResponseEntity.ok(updatedBilling);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillingById(@PathVariable Long id) {
        return billingService.getBillingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/invoice/{invoiceNumber}")
    public ResponseEntity<Billing> getBillingByInvoiceNumber(@PathVariable String invoiceNumber) {
        return billingService.getBillingByInvoiceNumber(invoiceNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBillings() {
        List<Billing> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Billing>> getBillingsByPatientId(@PathVariable Long patientId) {
        List<Billing> billings = billingService.getBillingsByPatientId(patientId);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Billing>> getBillingsByStatus(@PathVariable BillingStatus status) {
        List<Billing> billings = billingService.getBillingsByStatus(status);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Billing>> getBillingsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Billing> billings = billingService.getBillingsByDateRange(startDate, endDate);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Billing>> getOverdueBills() {
        List<Billing> billings = billingService.getOverdueBills();
        return ResponseEntity.ok(billings);
    }

    @PutMapping("/{id}/mark-paid")
    public ResponseEntity<Billing> markAsPaid(@PathVariable Long id,
                                             @RequestParam PaymentMethod paymentMethod,
                                             @RequestParam String transactionId) {
        Billing billing = billingService.markAsPaid(id, paymentMethod, transactionId);
        return ResponseEntity.ok(billing);
    }

    @PutMapping("/{id}/mark-overdue")
    public ResponseEntity<Billing> markAsOverdue(@PathVariable Long id) {
        Billing billing = billingService.markAsOverdue(id);
        return ResponseEntity.ok(billing);
    }

    @PostMapping("/appointment/{appointmentId}")
    public ResponseEntity<Billing> processBillingForAppointment(@PathVariable Long appointmentId) {
        Billing billing = billingService.processBillingForAppointment(appointmentId);
        return new ResponseEntity<>(billing, HttpStatus.CREATED);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal revenue = billingService.getTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        billingService.deleteBilling(id);
        return ResponseEntity.noContent().build();
    }
}