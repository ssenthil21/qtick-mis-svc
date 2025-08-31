# QTick MIS Backend

A Spring Boot 3.x REST API service built with Java 21 that serves as the backend for the QTick MIS (Management Information System) dashboard. The system provides comprehensive business management capabilities including dashboard analytics, sales pipeline management, client management, and business analytics.

## Features

- **Dashboard Management**: Real-time KPIs, trends, and business insights
- **Sales Pipeline**: Lead management and conversion tracking
- **Client Management**: Comprehensive client profiles and interaction history
- **Analytics**: Business performance analytics and reporting
- **Multi-tenant Architecture**: Support for multiple businesses and branches
- **JWT Authentication**: Secure API access with role-based permissions

## Technology Stack

- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.x** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - MySQL database access
- **Spring Data MongoDB** - MongoDB document storage
- **MapStruct** - Object mapping
- **Gradle** - Build system
- **MySQL** - Primary transactional database
- **MongoDB** - Analytics and activity timeline storage

## Getting Started

### Prerequisites

- Java 21 or higher
- MySQL 8.0 or higher
- MongoDB 4.4 or higher
- Gradle 8.5 or higher (or use the included wrapper)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd qtick-mis-backend
   ```

2. **Set up databases**
   - Create MySQL database: `qtick_local`
   - Create MongoDB database: `qtick_local`
   - Update connection details in `application-local.yml` if needed

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/api/v1`
   - Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`
   - Actuator Health: `http://localhost:8080/api/v1/actuator/health`

### Running Tests

```bash
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport
```

### Building for Production

```bash
# Build JAR file
./gradlew build

# Build Docker image
docker build -t qtick/mis-backend:latest .
```

## Configuration

The application supports multiple profiles:

- **local**: Local development with H2/embedded databases
- **staging**: Staging environment configuration
- **prod**: Production environment configuration

### Environment Variables

For staging and production deployments, configure these environment variables:

```bash
# Database Configuration
DB_URL=jdbc:mysql://mysql-host:3306/qtick_db
DB_USERNAME=qtick_user
DB_PASSWORD=secure_password

# MongoDB Configuration
MONGO_URI=mongodb://mongo-host:27017/qtick_db

# JWT Configuration
JWT_JWK_SET_URI=https://auth-server/.well-known/jwks.json
JWT_ISSUER_URI=https://auth-server

# Redis Configuration (for caching)
REDIS_HOST=redis-host
REDIS_PORT=6379
REDIS_PASSWORD=redis_password
```

## API Documentation

The API documentation is automatically generated using OpenAPI 3.0 and is available at:
- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/api-docs`

## Architecture

The application follows a layered architecture:

```
├── Controller Layer    # REST endpoints
├── Service Layer      # Business logic
├── Repository Layer   # Data access
├── Entity/Document    # Data models
├── DTO Layer         # Data transfer objects
├── Mapper Layer      # Object mapping
└── Security Layer    # Authentication & authorization
```

## Database Schema

- **MySQL**: Stores transactional data (enquiries, clients, bills, appointments)
- **MongoDB**: Stores analytics snapshots, activity timelines, and audit logs

## Contributing

1. Follow the existing code style and conventions
2. Write unit tests for new functionality
3. Update documentation as needed
4. Ensure all tests pass before submitting

## License

[Add your license information here]