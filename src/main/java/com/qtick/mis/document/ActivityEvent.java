package com.qtick.mis.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * MongoDB document representing an activity event for timeline tracking.
 * Stores events from various entities for comprehensive activity timelines.
 */
@Document(collection = "activity_events")
@CompoundIndexes({
    @CompoundIndex(name = "idx_activity_biz_created", def = "{'bizId': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_activity_entity", def = "{'bizId': 1, 'entityType': 1, 'entityId': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_activity_user", def = "{'bizId': 1, 'userId': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_activity_type", def = "{'bizId': 1, 'eventType': 1, 'createdAt': -1}")
})
public class ActivityEvent {

    @Id
    private String id;

    @Indexed
    private Long bizId;

    private Long branchId;

    @Indexed
    private String eventType;

    @Indexed
    private String entityType;

    @Indexed
    private Long entityId;

    private String title;

    private String description;

    private Map<String, Object> metadata = new HashMap<>();

    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;

    @Indexed
    private Long userId;

    private String userName;

    private String userRole;

    // Additional context fields
    private String clientName;

    private String clientPhone;

    private String serviceName;

    private String staffName;

    private String source;

    private String channel;

    // Constructors
    public ActivityEvent() {}

    public ActivityEvent(Long bizId, String eventType, String entityType, Long entityId, 
                        String title, String description, Long userId, String userName) {
        this.bizId = bizId;
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    // Helper methods
    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    @Override
    public String toString() {
        return "ActivityEvent{" +
                "id='" + id + '\'' +
                ", bizId=" + bizId +
                ", eventType='" + eventType + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}