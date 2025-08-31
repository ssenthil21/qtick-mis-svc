package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing an appointment scheduled with a client.
 */
@Entity
@Table(name = "appointments", indexes = {
    @Index(name = "idx_appointment_biz_date", columnList = "bizId, appointmentDate"),
    @Index(name = "idx_appointment_client", columnList = "client_id"),
    @Index(name = "idx_appointment_staff", columnList = "staffId"),
    @Index(name = "idx_appointment_status", columnList = "status"),
    @Index(name = "idx_appointment_branch", columnList = "branchId")
})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bizId;

    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    private LocalDateTime endTime;

    private Long serviceId;

    @Column(length = 100)
    private String serviceName;

    private Long staffId;

    @Column(length = 100)
    private String staffName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppointmentType appointmentType = AppointmentType.REGULAR;

    @Column(length = 500)
    private String notes;

    @Column(length = 500)
    private String cancellationReason;

    private LocalDateTime checkedInAt;

    private LocalDateTime checkedOutAt;

    private Integer durationMinutes;

    @Column(length = 50)
    private String bookingSource;

    @Column(length = 100)
    private String bookingReference;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedOn;

    // Constructors
    public Appointment() {}

    public Appointment(Long bizId, Client client, LocalDateTime appointmentDate, Long serviceId, String serviceName) {
        this.bizId = bizId;
        this.client = client;
        this.appointmentDate = appointmentDate;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDateTime getCheckedInAt() {
        return checkedInAt;
    }

    public void setCheckedInAt(LocalDateTime checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

    public LocalDateTime getCheckedOutAt() {
        return checkedOutAt;
    }

    public void setCheckedOutAt(LocalDateTime checkedOutAt) {
        this.checkedOutAt = checkedOutAt;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(String bookingSource) {
        this.bookingSource = bookingSource;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
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

    // Helper methods
    public void checkIn() {
        this.checkedInAt = LocalDateTime.now();
        this.status = AppointmentStatus.IN_PROGRESS;
    }

    public void checkOut() {
        this.checkedOutAt = LocalDateTime.now();
        this.status = AppointmentStatus.COMPLETED;
        
        if (this.checkedInAt != null) {
            this.durationMinutes = (int) java.time.Duration.between(this.checkedInAt, this.checkedOutAt).toMinutes();
        }
    }

    public void cancel(String reason) {
        this.status = AppointmentStatus.CANCELLED;
        this.cancellationReason = reason;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", serviceName='" + serviceName + '\'' +
                ", status=" + status +
                '}';
    }
}