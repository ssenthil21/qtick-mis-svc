package com.qtick.mis.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for role-based access control.
 * Provides convenient role checking for common business roles.
 */
public class RequireRole {

    /**
     * Requires ADMIN role.
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasRole('ADMIN')")
    public @interface Admin {
    }

    /**
     * Requires MANAGER role or higher (ADMIN, MANAGER).
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public @interface Manager {
    }

    /**
     * Requires SALES_REP role or higher (ADMIN, MANAGER, SALES_REP).
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SALES_REP')")
    public @interface SalesRep {
    }

    /**
     * Requires ANALYST role or higher (ADMIN, MANAGER, ANALYST).
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ANALYST')")
    public @interface Analyst {
    }

    /**
     * Requires VIEWER role or higher (any authenticated user).
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SALES_REP', 'ANALYST', 'VIEWER')")
    public @interface Viewer {
    }

    /**
     * Custom role requirement with SpEL expression.
     */
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("@tenantSecurityService.hasRole(authentication, #role)")
    public @interface Custom {
        String role();
    }
}