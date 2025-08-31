package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a loyalty points transaction for a client.
 */
@Entity
@Table(name = "points_transactions", indexes = {
    @Index(name = "idx_points_client", columnList = "client_id"),
    @Index(name = "idx_points_date", columnList = "transactionDate"),
    @Index(name = "idx_points_type", columnList = "transactionType")
})
public class PointsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private Integer points;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PointsTransactionType transactionType;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    private Long billId;

    private Long appointmentId;

    @Column(precision = 10, scale = 2)
    private BigDecimal billAmount;

    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PointsStatus status = PointsStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    // Constructors
    public PointsTransaction() {}

    public PointsTransaction(Client client, Integer points, PointsTransactionType transactionType, String description) {
        this.client = client;
        this.points = points;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public PointsTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(PointsTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PointsStatus getStatus() {
        return status;
    }

    public void setStatus(PointsStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "PointsTransaction{" +
                "id=" + id +
                ", points=" + points +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }
}