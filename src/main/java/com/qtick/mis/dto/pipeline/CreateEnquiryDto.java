package com.qtick.mis.dto.pipeline;

import com.qtick.mis.entity.EnquiryStage;
import com.qtick.mis.entity.EnquiryStatus;
import com.qtick.mis.entity.EnquiryType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CreateEnquiryDto {
    
    private Long branchId;
    private Long custId;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Enquiry type is required")
    private EnquiryType enqType;
    
    private Long srvcEnq;
    
    @NotNull(message = "Stage is required")
    private EnquiryStage stage;
    
    @NotNull(message = "Status is required")
    private EnquiryStatus status;
    
    private String source;
    private String channel;
    private Long assigneeId;
    private LocalDateTime reContactOn;
    private String nextAction;
    
    private Map<String, Object> attributes;
    private List<String> tags;
    
    // Constructors
    public CreateEnquiryDto() {}
    
    // Getters and Setters
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
    
    public LocalDateTime getReContactOn() { return reContactOn; }
    public void setReContactOn(LocalDateTime reContactOn) { this.reContactOn = reContactOn; }
    
    public String getNextAction() { return nextAction; }
    public void setNextAction(String nextAction) { this.nextAction = nextAction; }
    
    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}