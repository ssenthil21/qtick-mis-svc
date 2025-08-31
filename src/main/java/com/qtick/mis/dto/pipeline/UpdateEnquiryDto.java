package com.qtick.mis.dto.pipeline;

import com.qtick.mis.entity.EnquiryStage;
import com.qtick.mis.entity.EnquiryStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UpdateEnquiryDto {
    
    private EnquiryStage stage;
    private EnquiryStatus status;
    private LocalDateTime reContactOn;
    private Long assigneeId;
    private String nextAction;
    private LocalDateTime closureDate;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;
    
    @Email(message = "Invalid email format")
    private String email;
    
    private Map<String, Object> attributes;
    private List<String> tags;
    
    // Constructors
    public UpdateEnquiryDto() {}
    
    // Getters and Setters
    public EnquiryStage getStage() { return stage; }
    public void setStage(EnquiryStage stage) { this.stage = stage; }
    
    public EnquiryStatus getStatus() { return status; }
    public void setStatus(EnquiryStatus status) { this.status = status; }
    
    public LocalDateTime getReContactOn() { return reContactOn; }
    public void setReContactOn(LocalDateTime reContactOn) { this.reContactOn = reContactOn; }
    
    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    
    public String getNextAction() { return nextAction; }
    public void setNextAction(String nextAction) { this.nextAction = nextAction; }
    
    public LocalDateTime getClosureDate() { return closureDate; }
    public void setClosureDate(LocalDateTime closureDate) { this.closureDate = closureDate; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}