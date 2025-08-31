package com.qtick.mis.dto.client;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

public class TimelineEventDto {
    
    @NotNull
    private String id;
    
    @NotNull
    private String eventType;
    
    @NotNull
    private String entityType;
    
    private Long entityId;
    
    @NotNull
    private String description;
    
    @NotNull
    private LocalDateTime createdAt;
    
    private Long userId;
    private String userName;
    private String userRole;
    
    private Map<String, Object> metadata;
    
    // Constructors
    public TimelineEventDto() {}
    
    public TimelineEventDto(String id, String eventType, String entityType, String description, 
                           LocalDateTime createdAt) {
        this.id = id;
        this.eventType = eventType;
        this.entityType = entityType;
        this.description = description;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    
    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}