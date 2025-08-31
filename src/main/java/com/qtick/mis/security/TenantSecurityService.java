package com.qtick.mis.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Service for tenant-specific security checks and validations.
 * Provides methods for role-based access control and tenant data filtering.
 */
@Service
public class TenantSecurityService {

    /**
     * Checks if the authenticated user has the specified role.
     * 
     * @param authentication the authentication object
     * @param role the role to check
     * @return true if the user has the role, false otherwise
     */
    public boolean hasRole(Authentication authentication, String role) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        TenantContext context = TenantContextHolder.getContext();
        if (context == null) {
            return false;
        }

        return context.hasRole(role);
    }

    /**
     * Checks if the authenticated user has access to the specified business.
     * 
     * @param authentication the authentication object
     * @param bizId the business ID to check
     * @return true if the user has access to the business, false otherwise
     */
    public boolean hasAccessToBusiness(Authentication authentication, Long bizId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        TenantContext context = TenantContextHolder.getContext();
        if (context == null) {
            return false;
        }

        return context.getBizId() != null && context.getBizId().equals(bizId);
    }

    /**
     * Checks if the authenticated user has access to the specified branch.
     * 
     * @param authentication the authentication object
     * @param branchId the branch ID to check
     * @return true if the user has access to the branch, false otherwise
     */
    public boolean hasAccessToBranch(Authentication authentication, Long branchId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        TenantContext context = TenantContextHolder.getContext();
        if (context == null) {
            return false;
        }

        return context.hasAccessToBranch(branchId);
    }

    /**
     * Checks if the authenticated user can access data for the specified business and branch.
     * 
     * @param authentication the authentication object
     * @param bizId the business ID to check
     * @param branchId the branch ID to check (can be null for business-level access)
     * @return true if the user has access, false otherwise
     */
    public boolean canAccessData(Authentication authentication, Long bizId, Long branchId) {
        if (!hasAccessToBusiness(authentication, bizId)) {
            return false;
        }

        if (branchId != null && !hasAccessToBranch(authentication, branchId)) {
            return false;
        }

        return true;
    }

    /**
     * Gets the current tenant context.
     * 
     * @return the current tenant context, or null if not available
     */
    public TenantContext getCurrentTenantContext() {
        return TenantContextHolder.getContext();
    }

    /**
     * Validates that the current user has access to the specified business.
     * Throws SecurityException if access is denied.
     * 
     * @param bizId the business ID to validate access for
     * @throws SecurityException if access is denied
     */
    public void validateBusinessAccess(Long bizId) {
        TenantContext context = TenantContextHolder.getContext();
        if (context == null || !context.getBizId().equals(bizId)) {
            throw new SecurityException("Access denied to business: " + bizId);
        }
    }

    /**
     * Validates that the current user has access to the specified branch.
     * Throws SecurityException if access is denied.
     * 
     * @param branchId the branch ID to validate access for
     * @throws SecurityException if access is denied
     */
    public void validateBranchAccess(Long branchId) {
        TenantContext context = TenantContextHolder.getContext();
        if (context == null || !context.hasAccessToBranch(branchId)) {
            throw new SecurityException("Access denied to branch: " + branchId);
        }
    }
}