package com.qtick.mis.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Logging configuration for correlation ID tracking and structured logging.
 */
@Configuration
public class LoggingConfig {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";

    /**
     * Filter to add correlation ID to all requests for tracing purposes.
     */
    @Bean
    public OncePerRequestFilter correlationIdFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, 
                                          HttpServletResponse response, 
                                          FilterChain filterChain) throws ServletException, IOException {
                try {
                    String correlationId = request.getHeader(CORRELATION_ID_HEADER);
                    if (correlationId == null || correlationId.trim().isEmpty()) {
                        correlationId = UUID.randomUUID().toString();
                    }
                    
                    MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
                    response.setHeader(CORRELATION_ID_HEADER, correlationId);
                    
                    filterChain.doFilter(request, response);
                } finally {
                    MDC.clear();
                }
            }
        };
    }
}