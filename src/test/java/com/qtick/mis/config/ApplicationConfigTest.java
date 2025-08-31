package com.qtick.mis.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class to verify application configuration loads correctly.
 */
@SpringBootTest
@ActiveProfiles("test")
class ApplicationConfigTest {

    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
        // It validates that all configurations are properly set up
    }
}