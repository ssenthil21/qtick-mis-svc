package com.qtick.mis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Main application configuration class.
 * Enables JPA and MongoDB repositories with proper package scanning.
 */
@Configuration
@EnableAsync
@EnableJpaRepositories(basePackages = "com.qtick.mis.repository.jpa")
@EnableMongoRepositories(basePackages = "com.qtick.mis.repository.mongo")
public class ApplicationConfig {
    
    /**
     * Configure async executor for background tasks like event mirroring.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("qtick-async-");
        executor.initialize();
        return executor;
    }
}