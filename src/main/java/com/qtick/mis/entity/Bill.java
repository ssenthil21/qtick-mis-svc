package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a bill/invoice for services provided to a client.
 */
@Entity
@Table(name = "bills", indexes = {
    @Index(name = "idx_bill_biz_date", columnList = "bizId, billDate"),
    @Index(name = "idx_bill_client", columnList = "client_id"),
    @Index(name = "idx_bill_branch", columnList = "branchId"),
    @Index(name = "idx_bill_status", columnList = "status"),
    @Index(name = "idx_bill_created", columnList = "createdOn")
})
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bizId;

    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(length = 50, unique = true)
    private String billNumber;

    @Column(nullable = false)
    private LocalDate billDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal grossAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal netAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balanceAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private BillStatus status = BillStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMode paymentMode;

    @Column(length = 100)
    private String paymentReference;

    private LocalDateTime paymentDate;

    @Column(length = 500)
    private String notes;

    private Long staffId;

    @Column(length = 100)
    private String staffName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedOn;

    // Relationships
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillPayment> payments = new ArrayList<>();

    // Constructors
    public Bill() {}

    public Bill(Long bizId, Client client, String billNumber, LocalDate billDate, BigDecimal grossAmount, BigDecimal netAmount) {
        this.bizId = bizId;
        this.client = client;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.grossAmount = grossAmount;
        this.netAmount = netAmount;
        this.balanceAmount = netAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public List<BillPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<BillPayment> payments) {
        this.payments = payments;
    }

    // Helper methods
    public void addItem(BillItem item) {
        items.add(item);
        item.setBill(this);
    }

    public void addPayment(BillPayment payment) {
        payments.add(payment);
        payment.setBill(this);
        this.paidAmount = this.paidAmount.add(payment.getAmount());
        this.balanceAmount = this.netAmount.subtract(this.paidAmount);
        
        if (this.balanceAmount.compareTo(BigDecimal.ZERO) <= 0) {
            this.status = BillStatus.PAID;
        } else {
            this.status = BillStatus.PARTIAL;
        }
    }

    public void recalculateAmounts() {
        this.grossAmount = items.stream()
                .map(BillItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.netAmount = this.grossAmount.subtract(this.discountAmount).add(this.taxAmount);
        this.balanceAmount = this.netAmount.subtract(this.paidAmount);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billNumber='" + billNumber + '\'' +
                ", billDate=" + billDate +
                ", netAmount=" + netAmount +
                ", status=" + status +
                '}';
    }
}