package com.qtick.mis.dto.pipeline;

import com.qtick.mis.entity.ThreadType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

public class EnquiryThreadDto {
    
    @NotNull
    private Long id;
    
    @NotNull
    private Long enquiryId;
    
    @NotNull
    private ThreadType threadType;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    @NotNull
    private Long userId;
    
    private String userName;
    
    @NotNull
    private LocalDateTime createdOn;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public EnquiryThreadDto() {}
    
    public EnquiryThreadDto(Long id, Long enquiryId, ThreadType threadType, String message, 
                           Long userId, LocalDateTime createdOn) {
        this.id = id;
        this.enquiryId = enquiryId;
        this.threadType = threadType;
        this.message = message;
        this.userId = userId;
        this.createdOn = createdOn;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEnquiryId() { return enquiryId; }
    public void setEnquiryId(Long enquiryId) { this.enquiryId = enquiryId; }
    
    public ThreadType getThreadType() { return threadType; }
    public void setThreadType(ThreadType threadType) { this.threadType = threadType; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}