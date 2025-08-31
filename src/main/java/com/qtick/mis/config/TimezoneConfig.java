package com.qtick.mis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import jakarta.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Timezone configuration to ensure consistent handling of dates and times.
 * Sets default timezone to Asia/Singapore as per requirements.
 */
@Configuration
public class TimezoneConfig {

    /**
     * Set the default timezone for the JVM to Asia/Singapore.
     */
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Singapore")));
    }

    /**
     * Configure ObjectMapper with proper timezone handling.
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Singapore")));
        return mapper;
    }
}