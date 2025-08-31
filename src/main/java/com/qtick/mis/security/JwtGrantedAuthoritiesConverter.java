package com.qtick.mis.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter for extracting granted authorities from JWT claims.
 * Extracts roles from the 'roles' claim and converts them to Spring Security authorities.
 */
public class JwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES_CLAIM = "roles";
    private static final String AUTHORITY_PREFIX = "ROLE_";

    /**
     * Converts JWT roles claim to Spring Security authorities.
     * 
     * @param jwt the JWT token
     * @return collection of granted authorities
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object rolesClaim = jwt.getClaim(ROLES_CLAIM);
        
        if (rolesClaim == null) {
            return Collections.emptyList();
        }

        if (rolesClaim instanceof List<?> rolesList) {
            return rolesList.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(role -> new SimpleGrantedAuthority(AUTHORITY_PREFIX + role))
                .collect(Collectors.toList());
        }

        if (rolesClaim instanceof String rolesString) {
            return List.of(new SimpleGrantedAuthority(AUTHORITY_PREFIX + rolesString));
        }

        return Collections.emptyList();
    }
}