package com.qtick.mis.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration for JPA and MongoDB.
 * Enables auditing and transaction management.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableMongoAuditing
@EntityScan(basePackages = "com.qtick.mis.entity")
public class DatabaseConfig {
    
}