# Implementation Plan

- [x] 1. Set up project structure and core configuration







  - Create Spring Boot 3.x project with Java 21 using Gradle build system
  - Configure application.yml files for local, staging, and production profiles
  - Set up MySQL and MongoDB connection configurations
  - Add required dependencies: Spring Web, Spring Security, Spring Data JPA, Spring Data MongoDB, MapStruct, springdoc-openapi, Micrometer
  - _Requirements: 8.1, 8.2, 8.6_

- [x] 2. Implement security and multi-tenancy foundation





  - [x] 2.1 Create JWT security configuration


    - Implement JwtDecoder bean with RS256 signature validation
    - Create JwtAuthenticationConverter for extracting user claims
    - Configure SecurityFilterChain with JWT authentication
    - _Requirements: 5.1, 5.2_

  - [x] 2.2 Implement tenant context and filtering


    - Create TenantContext class to hold user, business, and branch information
    - Implement TenantContextHolder for thread-local storage
    - Create @PreAuthorize annotations for role-based access control
    - Write unit tests for security configuration and tenant filtering
    - _Requirements: 5.2, 5.3_

- [x] 3. Create core data models and entities



  - [x] 3.1 Implement MySQL entities



    - Create Enquiry entity with proper JPA annotations and relationships
    - Create EnquiryThread entity for communication history
    - Create Client entity with billing and appointment relationships
    - Create Bill, BillItem, and BillPayment entities
    - Create Appointment entity with client and service relationships
    - Write unit tests for entity relationships and constraints
    - _Requirements: 2.3, 3.2, 3.4, 3.5_

  - [x] 3.2 Implement MongoDB documents


    - Create ActivityEvent document for timeline events
    - Create DashboardSnapshot document for precomputed metrics
    - Create AuditLog document for security auditing
    - Write unit tests for MongoDB document structure and indexing
    - _Requirements: 6.1, 6.2, 5.4_

- [x] 4. Implement data access layer



  - [x] 4.1 Create MySQL repositories



    - Implement EnquiryRepository with custom query methods for pipeline filtering
    - Implement ClientRepository with search and filtering capabilities
    - Implement BillRepository with date range and client filtering
    - Implement AppointmentRepository with scheduling queries
    - Add proper indexing annotations and write repository tests
    - _Requirements: 2.2, 3.1, 3.4, 3.5, 6.4, 6.5_

  - [x] 4.2 Create MongoDB repositories


    - Implement ActivityEventRepository with timeline queries
    - Implement DashboardSnapshotRepository with date-based queries
    - Implement AuditLogRepository with security event queries
    - Add compound indexes for performance optimization
    - Write repository slice tests for MongoDB operations
    - _Requirements: 6.1, 6.2, 5.4, 6.5_

- [ ] 5. Create DTOs and mapping layer
  - [x] 5.1 Implement request and response DTOs



    - Create dashboard DTOs: DashboardSummaryDto, TrendDataDto, TopServiceDto, TopStaffDto, BusinessDetailsDto
    - Create pipeline DTOs: EnquiryDto, EnquiryDetailDto, CreateEnquiryDto, UpdateEnquiryDto, EnquiryThreadDto
    - Create client DTOs: ClientSummaryDto, ClientDetailDto, TimelineEventDto, BillDto
    - Create analytics DTOs: AnalyticsKpiDto, ServiceAnalyticsDto, StaffAnalyticsDto, FunnelAnalyticsDto
    - Add Jakarta Bean Validation annotations to all DTOs
    - _Requirements: 1.1, 2.1, 3.1, 4.1, 7.3, 8.4_

  - [x] 5.2 Implement MapStruct mappers



    - Create EnquiryMapper for entity-DTO conversions
    - Create ClientMapper with nested relationship mapping
    - Create BillMapper with payment and item details
    - Create ActivityEventMapper for timeline events
    - Write unit tests for all mapping operations
    - _Requirements: 8.4_

- [ ] 6. Implement service layer business logic




  - [ ] 6.1 Implement DashboardService


    - Create getSummary() method with KPI calculations and comparison logic
    - Create getTrends() method with time series data aggregation
    - Create getTopServices() and getTopStaff() methods with ranking algorithms
    - Create getHighlights() method using MongoDB snapshots
    - Create getBusinessDetails() method with daily job statistics
    - Write comprehensive unit tests with mocked repositories
    - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6_

  - [ ] 6.2 Implement PipelineService
    - Create getSummary() method with funnel calculations and SLA tracking
    - Create searchEnquiries() method with multi-criteria filtering and pagination
    - Create createEnquiry() and updateEnquiry() methods with validation
    - Create getThreads() and addThread() methods with event mirroring to MongoDB
    - Implement tenant filtering for all pipeline operations
    - Write unit tests covering all business logic scenarios
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6_

  - [ ] 6.3 Implement ClientService
    - Create searchClients() method with advanced filtering capabilities
    - Create getClient() method with comprehensive profile data
    - Create getTimeline() method merging MySQL and MongoDB events
    - Create getBills(), getAppointments(), getServices(), getPoints(), getDevices() methods
    - Implement performance optimization for timeline loading
    - Write unit tests for all client operations and timeline merging
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8_

  - [ ] 6.4 Implement AnalyticsService
    - Create getKpis() method with aggregate calculations
    - Create getServiceAnalytics() and getStaffAnalytics() methods with performance metrics
    - Create getFunnelAnalytics() method with conversion rate calculations
    - Create getMarketingAnalytics() and getRetentionAnalytics() methods
    - Implement caching for expensive analytical queries
    - Write unit tests ensuring accuracy within ±0.5% variance requirement
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7_

- [ ] 7. Implement REST controllers
  - [ ] 7.1 Create DashboardController
    - Implement all dashboard endpoints with proper request validation
    - Add tenant context extraction from JWT claims
    - Implement timezone handling for date parameters
    - Add comprehensive error handling and logging
    - Write controller tests using @WebMvcTest
    - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 7.1, 7.6_

  - [ ] 7.2 Create PipelineController
    - Implement all pipeline endpoints with pagination support
    - Add idempotency key handling for POST operations
    - Implement proper HTTP status codes and response headers
    - Add request/response logging with PII masking
    - Write controller tests with security context
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 7.1, 7.2, 7.4_

  - [ ] 7.3 Create ClientController
    - Implement all client endpoints with performance optimization
    - Add proper caching headers for timeline data
    - Implement streaming for large result sets
    - Add comprehensive input validation
    - Write controller tests covering all endpoints
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 7.1, 7.2_

  - [ ] 7.4 Create AnalyticsController
    - Implement all analytics endpoints with caching support
    - Add export functionality for analytical data
    - Implement proper role-based access for sensitive analytics
    - Add performance monitoring for heavy analytical queries
    - Write controller tests with different user roles
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 7.1, 7.2_

- [ ] 8. Implement cross-cutting concerns
  - [ ] 8.1 Create global exception handler
    - Implement GlobalExceptionHandler with proper error response structure
    - Add specific handlers for validation, not found, access denied, and data integrity exceptions
    - Implement correlation ID tracking for error tracing
    - Add structured logging for all exceptions
    - Write tests for all exception scenarios
    - _Requirements: 7.3, 5.5_

  - [ ] 8.2 Implement audit logging and event mirroring
    - Create AuditService for tracking all write operations
    - Implement EventMirroringService for MySQL to MongoDB synchronization
    - Add async processing for event mirroring to avoid performance impact
    - Create audit log queries for security monitoring
    - Write tests for audit logging and event mirroring
    - _Requirements: 5.4, 6.1, 6.2_

  - [ ] 8.3 Add observability and monitoring
    - Configure Micrometer metrics for JVM, HTTP requests, and database operations
    - Add custom business metrics for enquiry conversion rates and response times
    - Implement structured JSON logging with correlation IDs
    - Configure Actuator health checks for databases and external services
    - Write tests for metrics collection and health endpoints
    - _Requirements: 6.3, 8.5_

- [ ] 9. Implement caching and performance optimization
  - [ ] 9.1 Add Redis caching layer
    - Configure Redis connection and cache manager
    - Add @Cacheable annotations to expensive service methods
    - Implement cache eviction strategies for data consistency
    - Add cache metrics and monitoring
    - Write tests for cache behavior and eviction
    - _Requirements: 6.2, 6.3_

  - [ ] 9.2 Optimize database queries and indexing
    - Add database indexes for all query patterns identified in requirements
    - Implement query optimization for dashboard aggregations
    - Add connection pooling configuration for both MySQL and MongoDB
    - Implement read replicas support for analytical queries
    - Write performance tests to validate P95 response time requirements
    - _Requirements: 6.4, 6.5, 6.3_

- [ ] 10. Create comprehensive test suite
  - [ ] 10.1 Write integration tests
    - Create @SpringBootTest integration tests for full application context
    - Implement Testcontainers for MySQL and MongoDB integration testing
    - Create multi-tenant data isolation tests
    - Add end-to-end API tests using RestAssured
    - Write performance tests using k6 for load testing
    - _Requirements: 6.3_

  - [ ] 10.2 Add contract and security tests
    - Create OpenAPI contract tests to ensure API specification compliance
    - Implement security tests for JWT validation and RBAC enforcement
    - Add tests for tenant data isolation and access control
    - Create tests for audit logging and PII masking
    - Write tests for error handling and edge cases
    - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 11. Configure deployment and documentation
  - [ ] 11.1 Create deployment configurations
    - Create Dockerfile with multi-stage build for production optimization
    - Implement Kubernetes deployment manifests with proper resource limits
    - Configure environment-specific application properties
    - Add database migration scripts using Flyway
    - Create deployment scripts and CI/CD pipeline configuration
    - _Requirements: 8.6_

  - [ ] 11.2 Generate API documentation
    - Configure springdoc-openapi for automatic OpenAPI specification generation
    - Add comprehensive API documentation with examples
    - Create Postman collection for API testing
    - Write deployment and operational documentation
    - Add performance tuning and troubleshooting guides
    - _Requirements: 7.5, 8.5_