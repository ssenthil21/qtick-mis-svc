package com.qtick.mis.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Thread-local holder for tenant context information.
 * Provides utilities to extract and access tenant context from JWT claims.
 */
public class TenantContextHolder {

    private static final ThreadLocal<TenantContext> contextHolder = new ThreadLocal<>();

    /**
     * Sets the tenant context for the current thread.
     * 
     * @param context the tenant context to set
     */
    public static void setContext(TenantContext context) {
        contextHolder.set(context);
    }

    /**
     * Gets the tenant context for the current thread.
     * 
     * @return the tenant context, or null if not set
     */
    public static TenantContext getContext() {
        return contextHolder.get();
    }

    /**
     * Clears the tenant context for the current thread.
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * Extracts and sets tenant context from the current Spring Security context.
     * Should be called after JWT authentication is complete.
     * 
     * @return the extracted tenant context, or null if extraction fails
     */
    public static TenantContext extractAndSetFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            TenantContext context = extractFromJwt(jwt);
            setContext(context);
            return context;
        }
        
        return null;
    }

    /**
     * Extracts tenant context from JWT claims.
     * 
     * @param jwt the JWT token
     * @return the extracted tenant context
     */
    public static TenantContext extractFromJwt(Jwt jwt) {
        // Extract user ID from 'userId' claim or fallback to 'sub'
        Long userId = extractLongClaim(jwt, "userId");
        if (userId == null) {
            String sub = jwt.getSubject();
            try {
                userId = Long.parseLong(sub);
            } catch (NumberFormatException e) {
                // If subject is not a number, use null
                userId = null;
            }
        }

        // Extract business ID
        Long bizId = extractLongClaim(jwt, "bizId");

        // Extract branch IDs
        Set<Long> branchIds = extractLongSetClaim(jwt, "branchIds");

        // Extract roles
        Set<String> roles = extractStringSetClaim(jwt, "roles");

        // Extract timezone
        String timezone = jwt.getClaimAsString("timezone");

        // Get subject
        String subject = jwt.getSubject();

        return new TenantContext(userId, bizId, branchIds, roles, timezone, subject);
    }

    /**
     * Extracts a Long claim from JWT.
     * 
     * @param jwt the JWT token
     * @param claimName the claim name
     * @return the Long value, or null if not present or invalid
     */
    private static Long extractLongClaim(Jwt jwt, String claimName) {
        Object claim = jwt.getClaim(claimName);
        if (claim == null) {
            return null;
        }
        
        if (claim instanceof Number number) {
            return number.longValue();
        }
        
        if (claim instanceof String string) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        return null;
    }

    /**
     * Extracts a Set of Long values from JWT claim.
     * 
     * @param jwt the JWT token
     * @param claimName the claim name
     * @return the Set of Long values, empty set if not present or invalid
     */
    private static Set<Long> extractLongSetClaim(Jwt jwt, String claimName) {
        Object claim = jwt.getClaim(claimName);
        if (claim == null) {
            return Set.of();
        }
        
        if (claim instanceof List<?> list) {
            return list.stream()
                .filter(item -> item instanceof Number || item instanceof String)
                .map(item -> {
                    if (item instanceof Number number) {
                        return number.longValue();
                    } else {
                        try {
                            return Long.parseLong(item.toString());
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
        }
        
        return Set.of();
    }

    /**
     * Extracts a Set of String values from JWT claim.
     * 
     * @param jwt the JWT token
     * @param claimName the claim name
     * @return the Set of String values, empty set if not present or invalid
     */
    private static Set<String> extractStringSetClaim(Jwt jwt, String claimName) {
        Object claim = jwt.getClaim(claimName);
        if (claim == null) {
            return Set.of();
        }
        
        if (claim instanceof List<?> list) {
            return list.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .collect(Collectors.toSet());
        }
        
        if (claim instanceof String string) {
            return Set.of(string);
        }
        
        return Set.of();
    }
}