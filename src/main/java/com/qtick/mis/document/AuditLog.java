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
 * MongoDB document representing audit logs for security and compliance tracking.
 * Captures all write operations and security events for audit trails.
 */
@Document(collection = "audit_logs")
@CompoundIndexes({
    @CompoundIndex(name = "idx_audit_biz_created", def = "{'bizId': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_audit_user", def = "{'bizId': 1, 'userId': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_audit_entity", def = "{'bizId': 1, 'entityType': 1, 'entityId': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_audit_action", def = "{'bizId': 1, 'action': 1, 'createdAt': -1}"),
    @CompoundIndex(name = "idx_audit_severity", def = "{'bizId': 1, 'severity': 1, 'createdAt': -1}")
})
public class AuditLog {

    @Id
    private String id;

    @Indexed
    private Long bizId;

    private Long branchId;

    @Indexed
    private String action;

    @Indexed
    private String entityType;

    @Indexed
    private Long entityId;

    private String entityName;

    @Indexed
    private Long userId;

    private String userName;

    private String userRole;

    @Indexed
    private String severity; // INFO, WARN, ERROR, CRITICAL

    private String description;

    private Map<String, Object> oldValues = new HashMap<>();

    private Map<String, Object> newValues = new HashMap<>();

    private Map<String, Object> metadata = new HashMap<>();

    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;

    // Request context
    private String ipAddress;

    private String userAgent;

    private String sessionId;

    private String correlationId;

    private String requestPath;

    private String httpMethod;

    // Security context
    private String jwtSubject;

    private String tenantContext;

    private Boolean successful = true;

    private String errorMessage;

    private String stackTrace;

    // Constructors
    public AuditLog() {}

    public AuditLog(Long bizId, String action, String entityType, Long entityId, 
                   Long userId, String userName, String severity, String description) {
        this.bizId = bizId;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.userId = userId;
        this.userName = userName;
        this.severity = severity;
        this.description = description;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getOldValues() {
        return oldValues;
    }

    public void setOldValues(Map<String, Object> oldValues) {
        this.oldValues = oldValues != null ? oldValues : new HashMap<>();
    }

    public Map<String, Object> getNewValues() {
        return newValues;
    }

    public void setNewValues(Map<String, Object> newValues) {
        this.newValues = newValues != null ? newValues : new HashMap<>();
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getJwtSubject() {
        return jwtSubject;
    }

    public void setJwtSubject(String jwtSubject) {
        this.jwtSubject = jwtSubject;
    }

    public String getTenantContext() {
        return tenantContext;
    }

    public void setTenantContext(String tenantContext) {
        this.tenantContext = tenantContext;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    // Helper methods
    public void addOldValue(String key, Object value) {
        if (this.oldValues == null) {
            this.oldValues = new HashMap<>();
        }
        this.oldValues.put(key, value);
    }

    public void addNewValue(String key, Object value) {
        if (this.newValues == null) {
            this.newValues = new HashMap<>();
        }
        this.newValues.put(key, value);
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public void markAsError(String errorMessage, String stackTrace) {
        this.successful = false;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
        this.severity = "ERROR";
    }

    public boolean isSecurityEvent() {
        return "SECURITY".equals(this.entityType) || 
               "LOGIN".equals(this.action) || 
               "LOGOUT".equals(this.action) ||
               "ACCESS_DENIED".equals(this.action) ||
               "PERMISSION_VIOLATION".equals(this.action);
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id='" + id + '\'' +
                ", bizId=" + bizId +
                ", action='" + action + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", userId=" + userId +
                ", severity='" + severity + '\'' +
                ", successful=" + successful +
                ", createdAt=" + createdAt +
                '}';
    }
}