package com.qtick.mis.security;

import java.util.Set;

/**
 * Holds tenant-specific context information extracted from JWT claims.
 * Contains user, business, and branch information for multi-tenant operations.
 */
public class TenantContext {
    
    private final Long userId;
    private final Long bizId;
    private final Set<Long> branchIds;
    private final Set<String> roles;
    private final String timezone;
    private final String subject;

    /**
     * Creates a new TenantContext with the specified values.
     * 
     * @param userId the user ID
     * @param bizId the business ID
     * @param branchIds the set of branch IDs the user has access to
     * @param roles the set of user roles
     * @param timezone the user's timezone
     * @param subject the JWT subject claim
     */
    public TenantContext(Long userId, Long bizId, Set<Long> branchIds, 
                        Set<String> roles, String timezone, String subject) {
        this.userId = userId;
        this.bizId = bizId;
        this.branchIds = branchIds != null ? Set.copyOf(branchIds) : Set.of();
        this.roles = roles != null ? Set.copyOf(roles) : Set.of();
        this.timezone = timezone != null ? timezone : "Asia/Singapore";
        this.subject = subject;
    }

    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Gets the business ID.
     * 
     * @return the business ID
     */
    public Long getBizId() {
        return bizId;
    }

    /**
     * Gets the set of branch IDs the user has access to.
     * 
     * @return immutable set of branch IDs
     */
    public Set<Long> getBranchIds() {
        return branchIds;
    }

    /**
     * Gets the set of user roles.
     * 
     * @return immutable set of roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * Gets the user's timezone.
     * 
     * @return the timezone string
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Gets the JWT subject claim.
     * 
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Checks if the user has the specified role.
     * 
     * @param role the role to check
     * @return true if the user has the role, false otherwise
     */
    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    /**
     * Checks if the user has access to the specified branch.
     * 
     * @param branchId the branch ID to check
     * @return true if the user has access to the branch, false otherwise
     */
    public boolean hasAccessToBranch(Long branchId) {
        return branchIds.isEmpty() || branchIds.contains(branchId);
    }

    @Override
    public String toString() {
        return "TenantContext{" +
                "userId=" + userId +
                ", bizId=" + bizId +
                ", branchIds=" + branchIds +
                ", roles=" + roles +
                ", timezone='" + timezone + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}