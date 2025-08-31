package com.qtick.mis.dto.pipeline;

import com.qtick.mis.entity.ThreadType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class CreateThreadDto {
    
    @NotNull(message = "Thread type is required")
    private ThreadType threadType;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public CreateThreadDto() {}
    
    public CreateThreadDto(ThreadType threadType, String message) {
        this.threadType = threadType;
        this.message = message;
    }
    
    // Getters and Setters
    public ThreadType getThreadType() { return threadType; }
    public void setThreadType(ThreadType threadType) { this.threadType = threadType; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}