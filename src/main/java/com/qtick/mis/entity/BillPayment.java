package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a payment made against a bill.
 */
@Entity
@Table(name = "bill_payments", indexes = {
    @Index(name = "idx_payment_bill", columnList = "bill_id"),
    @Index(name = "idx_payment_date", columnList = "paymentDate"),
    @Index(name = "idx_payment_mode", columnList = "paymentMode")
})
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PaymentMode paymentMode;

    @Column(length = 100)
    private String paymentReference;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(length = 500)
    private String notes;

    private Long processedBy;

    @Column(length = 100)
    private String processedByName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    // Constructors
    public BillPayment() {}

    public BillPayment(Bill bill, BigDecimal amount, PaymentMode paymentMode, LocalDateTime paymentDate) {
        this.bill = bill;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Long processedBy) {
        this.processedBy = processedBy;
    }

    public String getProcessedByName() {
        return processedByName;
    }

    public void setProcessedByName(String processedByName) {
        this.processedByName = processedByName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "BillPayment{" +
                "id=" + id +
                ", amount=" + amount +
                ", paymentMode=" + paymentMode +
                ", paymentDate=" + paymentDate +
                '}';
    }
}