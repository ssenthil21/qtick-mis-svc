package com.qtick.mis.config;

import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Health check configuration for monitoring database connectivity.
 */
@Configuration
public class HealthConfig {

    /**
     * Custom health indicator for MySQL database connectivity.
     */
    @Bean
    public HealthIndicator mysqlHealthIndicator(JdbcTemplate jdbcTemplate) {
        return () -> {
            try {
                jdbcTemplate.queryForObject("SELECT 1", Integer.class);
                return Health.up()
                        .withDetail("database", "MySQL")
                        .withDetail("status", "Connected")
                        .build();
            } catch (Exception e) {
                return Health.down()
                        .withDetail("database", "MySQL")
                        .withDetail("status", "Connection failed")
                        .withDetail("error", e.getMessage())
                        .build();
            }
        };
    }

    /**
     * Custom health indicator for MongoDB connectivity.
     */
    @Bean
    public HealthIndicator mongoHealthIndicator(MongoTemplate mongoTemplate) {
        return () -> {
            try {
                mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
                return Health.up()
                        .withDetail("database", "MongoDB")
                        .withDetail("status", "Connected")
                        .build();
            } catch (Exception e) {
                return Health.down()
                        .withDetail("database", "MongoDB")
                        .withDetail("status", "Connection failed")
                        .withDetail("error", e.getMessage())
                        .build();
            }
        };
    }
}