package com.qtick.mis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Integration test for the main application class.
 */
@SpringBootTest
@ActiveProfiles("test")
class QTickMisBackendApplicationIntegrationTest {

    @Test
    void applicationStartsSuccessfully() {
        // This test verifies that the Spring Boot application starts successfully
        // with all configurations and dependencies properly wired
    }
}