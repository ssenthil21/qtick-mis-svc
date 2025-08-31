package com.qtick.mis.dto.client;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentDto {
    
    @NotNull
    private Long appointmentId;
    
    @NotNull
    private Long custId;
    
    private String clientName;
    
    @NotNull
    private LocalDateTime appointmentDate;
    
    @NotNull
    private String status;
    
    private Long serviceId;
    private String serviceName;
    private Long staffId;
    private String staffName;
    private String notes;
    
    @NotNull
    private LocalDateTime createdOn;
    
    // Constructors
    public AppointmentDto() {}
    
    public AppointmentDto(Long appointmentId, Long custId, LocalDateTime appointmentDate, String status) {
        this.appointmentId = appointmentId;
        this.custId = custId;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    
    public Long getCustId() { return custId; }
    public void setCustId(Long custId) { this.custId = custId; }
    
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
}