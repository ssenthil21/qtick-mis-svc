package com.qtick.mis.dto.pipeline;

import com.qtick.mis.entity.EnquiryStage;
import com.qtick.mis.entity.EnquiryStatus;
import com.qtick.mis.entity.EnquiryType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EnquiryDto {
    
    @NotNull
    private Long id;
    
    @NotNull
    private Long bizId;
    
    private Long branchId;
    private Long custId;
    
    @NotNull
    private String name;
    
    private String phone;
    private String email;
    
    @NotNull
    private EnquiryType enqType;
    
    private Long srvcEnq;
    
    @NotNull
    private EnquiryStage stage;
    
    @NotNull
    private EnquiryStatus status;
    
    private String source;
    private String channel;
    private Long assigneeId;
    private String assigneeName;
    private LocalDateTime reContactOn;
    private String nextAction;
    private LocalDateTime lastTouchDate;
    private LocalDateTime closureDate;
    
    @NotNull
    private LocalDateTime createdOn;
    
    @NotNull
    private LocalDateTime updatedOn;
    
    // Constructors
    public EnquiryDto() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getBizId() { return bizId; }
    public void setBizId(Long bizId) { this.bizId = bizId; }
    
    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }
    
    public Long getCustId() { return custId; }
    public void setCustId(Long custId) { this.custId = custId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public EnquiryType getEnqType() { return enqType; }
    public void setEnqType(EnquiryType enqType) { this.enqType = enqType; }
    
    public Long getSrvcEnq() { return srvcEnq; }
    public void setSrvcEnq(Long srvcEnq) { this.srvcEnq = srvcEnq; }
    
    public EnquiryStage getStage() { return stage; }
    public void setStage(EnquiryStage stage) { this.stage = stage; }
    
    public EnquiryStatus getStatus() { return status; }
    public void setStatus(EnquiryStatus status) { this.status = status; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    
    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    
    public String getAssigneeName() { return assigneeName; }
    public void setAssigneeName(String assigneeName) { this.assigneeName = assigneeName; }
    
    public LocalDateTime getReContactOn() { return reContactOn; }
    public void setReContactOn(LocalDateTime reContactOn) { this.reContactOn = reContactOn; }
    
    public String getNextAction() { return nextAction; }
    public void setNextAction(String nextAction) { this.nextAction = nextAction; }
    
    public LocalDateTime getLastTouchDate() { return lastTouchDate; }
    public void setLastTouchDate(LocalDateTime lastTouchDate) { this.lastTouchDate = lastTouchDate; }
    
    public LocalDateTime getClosureDate() { return closureDate; }
    public void setClosureDate(LocalDateTime closureDate) { this.closureDate = closureDate; }
    
    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
    
    public LocalDateTime getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(LocalDateTime updatedOn) { this.updatedOn = updatedOn; }
}