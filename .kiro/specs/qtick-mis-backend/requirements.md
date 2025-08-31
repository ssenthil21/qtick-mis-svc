# Requirements Document

## Introduction

The QTick MIS Backend is a Spring Boot Java 21 REST API service that will replace sample data in the QTick MIS dashboard with real APIs. The system integrates with MySQL as the system-of-record and MongoDB for activity timelines and analytics snapshots. The backend serves four main UI pages: Dashboard, Sales Pipeline, Client Management, and Analytics, supporting multi-tenant operations with role-based access control.

## Requirements

### Requirement 1: Dashboard Data Management

**User Story:** As a business manager, I want to view real-time dashboard metrics and trends, so that I can monitor business performance and make informed decisions.

#### Acceptance Criteria

1. WHEN a user requests dashboard summary THEN the system SHALL return KPIs including grossSales, netSales, bills, avgBill, newLeads, totalLeads, missedLeads, appointments, and returningCustomers within 400ms for cached data
2. WHEN a user requests trend data THEN the system SHALL return time series data grouped by day/week/month for specified metrics
3. WHEN a user requests top services THEN the system SHALL return ranked services by revenue/jobs/contribution with configurable limits
4. WHEN a user requests top staff THEN the system SHALL return staff rankings by revenue/jobs/rating with configurable limits
5. WHEN a user requests dashboard highlights THEN the system SHALL return best/worst days, spikes, and anomalies from precomputed snapshots
6. WHEN a user requests business details THEN the system SHALL return existing client business information filtered by business type with daily job statistics including appointments, walk-ins, and total bills
7. IF comparison period is provided THEN the system SHALL calculate and return percentage deltas for key metrics

### Requirement 2: Sales Pipeline Management

**User Story:** As a sales representative, I want to manage enquiries through the sales pipeline, so that I can track leads and convert them to customers effectively.

#### Acceptance Criteria

1. WHEN a user requests pipeline summary THEN the system SHALL return funnel data by stage, source, channel, and SLA buckets (overdue, due today, upcoming)
2. WHEN a user searches enquiries THEN the system SHALL return paginated results with filters for stage, source, assignee, service, channel, search terms, tags, and branch
3. WHEN a user creates a new enquiry THEN the system SHALL validate required fields and persist to MySQL with audit trail
4. WHEN a user updates an enquiry THEN the system SHALL allow partial updates for stage, status, reContactOn, assigneeId, nextAction, closureDate, and attributes
5. WHEN a user adds enquiry thread THEN the system SHALL persist to MySQL and mirror to MongoDB for timeline display within 2 seconds
6. WHEN a user requests enquiry threads THEN the system SHALL return chronological message/activity list including notes, calls, WhatsApp, and email interactions

### Requirement 3: Client Management and Profiles

**User Story:** As a business user, I want to access comprehensive client information and interaction history, so that I can provide personalized service and track customer relationships.

#### Acceptance Criteria

1. WHEN a user searches clients THEN the system SHALL return results with filters for name/phone/email, tags, loyalty points, spending, and last visit date
2. WHEN a user requests client details THEN the system SHALL return master data plus KPIs including contact info, demographics, preferences, and loyalty status
3. WHEN a user requests client timeline THEN the system SHALL merge MySQL events with MongoDB activity events and load 50 latest events within 600ms
4. WHEN a user requests client bills THEN the system SHALL return paginated bill headers with items and payments
5. WHEN a user requests client appointments THEN the system SHALL return past and upcoming appointments
6. WHEN a user requests client services THEN the system SHALL return owned and frequently used services
7. WHEN a user requests client points THEN the system SHALL return loyalty ledger and current balance
8. WHEN a user requests client devices THEN the system SHALL return app/device footprint and last login information

### Requirement 4: Analytics and Reporting

**User Story:** As an analyst, I want to access comprehensive business analytics and reports, so that I can analyze performance trends and generate insights for business improvement.

#### Acceptance Criteria

1. WHEN a user requests KPI analytics THEN the system SHALL return aggregate metrics for the selected period with comparison deltas
2. WHEN a user requests service analytics THEN the system SHALL return service-level data including revenue, jobs, mix, and year-over-year comparisons
3. WHEN a user requests staff analytics THEN the system SHALL return staff performance including revenue, jobs, utilization, and average ratings
4. WHEN a user requests funnel analytics THEN the system SHALL return lead conversion rates, stage-to-stage progression, win rates, and cycle times
5. WHEN a user requests marketing analytics THEN the system SHALL return campaign performance including uptake, bookings, revenue, and ROI
6. WHEN a user requests retention analytics THEN the system SHALL return visit cohorts, returning customer rates, and lifecycle analysis
7. IF analytics data matches BOH SQL THEN the system SHALL ensure accuracy within ±0.5% variance

### Requirement 5: Authentication and Authorization

**User Story:** As a system administrator, I want to control user access based on roles and tenant boundaries, so that data security and privacy are maintained across different business entities.

#### Acceptance Criteria

1. WHEN a user authenticates THEN the system SHALL validate JWT tokens with RS256 signature and extract claims for sub, roles, bizId, branchIds, and userId
2. WHEN a user accesses any endpoint THEN the system SHALL enforce role-based permissions (ADMIN, MANAGER, SALES_REP, ANALYST, VIEWER)
3. WHEN a user queries data THEN the system SHALL automatically filter results by business and branch scope from JWT claims
4. WHEN a user performs write operations THEN the system SHALL create audit logs capturing who, what, and when
5. WHEN sensitive data is logged THEN the system SHALL mask PII information
6. IF a user lacks required permissions THEN the system SHALL return 403 Forbidden with appropriate error message

### Requirement 6: Data Integration and Performance

**User Story:** As a system user, I want fast and reliable data access across different data sources, so that the application performs efficiently under normal and peak loads.

#### Acceptance Criteria

1. WHEN transactional data is written THEN the system SHALL persist to MySQL as system-of-record and mirror relevant events to MongoDB
2. WHEN dashboard data is requested THEN the system SHALL prefer MongoDB snapshots when available, otherwise compute on-the-fly with caching
3. WHEN API responses are generated THEN the system SHALL achieve P95 response times under 300ms for cached data and under 800ms for heavy aggregates
4. WHEN database queries are executed THEN the system SHALL utilize proper indexes on (bizId, billDate), (bizId, createdOn), (enqStage, lastTouchDate), and foreign key columns
5. WHEN MongoDB queries are executed THEN the system SHALL use compound indexes on (bizId, createdAt) and (bizId, day) for daily snapshots
6. WHEN system experiences load THEN the system SHALL maintain 99.9% availability through stateless node design

### Requirement 7: API Standards and Conventions

**User Story:** As a frontend developer, I want consistent and well-documented APIs, so that I can integrate efficiently and handle errors gracefully.

#### Acceptance Criteria

1. WHEN APIs are accessed THEN the system SHALL use base path /api/v1 with Bearer token authorization
2. WHEN paginated data is requested THEN the system SHALL support Spring Pageable conventions with page, size, and sort parameters
3. WHEN errors occur THEN the system SHALL return structured responses with code, message, and details array
4. WHEN idempotent operations are needed THEN the system SHALL support Idempotency-Key header for retriable POSTs
5. WHEN API documentation is generated THEN the system SHALL provide OpenAPI specification via springdoc-openapi
6. WHEN timezone-sensitive data is handled THEN the system SHALL store in UTC and render in requested timezone (default Asia/Singapore)

### Requirement 8: System Architecture and Technology Stack

**User Story:** As a system architect, I want a modern, maintainable, and scalable backend architecture, so that the system can evolve with business needs and handle growth.

#### Acceptance Criteria

1. WHEN the system is built THEN it SHALL use Spring Boot 3.x with Java 21 and Gradle/Maven build system
2. WHEN data persistence is implemented THEN the system SHALL use Spring Data JPA for MySQL and Spring Data MongoDB for MongoDB
3. WHEN validation is required THEN the system SHALL use Jakarta Bean Validation annotations
4. WHEN object mapping is needed THEN the system SHALL use MapStruct for efficient mapping between DTOs and entities
5. WHEN observability is implemented THEN the system SHALL include Actuator, Micrometer metrics, structured JSON logging, and trace correlation
6. WHEN deployment is configured THEN the system SHALL support Docker/Kubernetes with profiles for local, staging, and production environments
7. WHEN testing is implemented THEN the system SHALL include unit tests, JPA/MongoDB slice tests, contract tests with RestAssured, and k6 load tests