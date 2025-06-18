# FX Data Warehouse 💱

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-Open%20Source-green.svg)](LICENSE)

A robust Foreign Exchange deals data warehouse system designed to accept, validate, and persist FX deal details into a structured database.

---

## 📑 Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Testing](#testing)
- [Development](#development)

---

## 🎯 Overview

The FX Data Warehouse is a Spring Boot application built for **Bloomberg** that processes Foreign Exchange deals with comprehensive validation, deduplication, and persistence capabilities. The system ensures data integrity while maintaining a **no-rollback policy** for imported deals.

### Business Requirements
- Accept FX deal details and persist them into a database
- Validate all deal fields with comprehensive business rules
- Prevent duplicate deal imports (same deal ID)
- No rollback allowed - each deal processed independently
- Detailed logging and audit trail

---

## ✨ Key Features

### 🔍 **Comprehensive Validation**
- **Unique Deal ID** verification
- **ISO 4217 Currency Code** validation
- **Timestamp format** validation
- **Amount** validation (positive values)
- **Currency pairs** validation (fromCurrency ≠ toCurrency)

### 🛡️ **Data Integrity**
- Duplicate detection and prevention
- Robust error handling with detailed messages
- No rollback policy implementation
- Transaction management

### 📊 **Monitoring & Observability**
- Structured logging with SLF4J
- Spring Boot Actuator for health checks
- Detailed audit trail for all operations

### 🔧 **Developer Experience**
- OpenAPI/Swagger documentation
- Docker containerization
- Database migrations with Liquibase
- Comprehensive test suite

---

## 🛠️ Tech Stack

### **Core Technologies**
- **Java 21** - Latest LTS version
- **Spring Boot 3.5.0** - Enterprise framework
- **Spring Data JPA** - Data persistence
- **PostgreSQL 16** - Primary database
- **Liquibase** - Database migration

### **Development & Testing**
- **Maven** - Build automation
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **AssertJ** - Fluent assertions
- **Lombok** - Boilerplate reduction

### **Documentation & Monitoring**
- **SpringDoc OpenAPI** - API documentation
- **Spring Boot Actuator** - Application monitoring
- **SLF4J** - Logging framework

### **DevOps**
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Makefile** - Build automation

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/progressoft/clustereddatawarehouse/
│   │   ├── config/                 # Configuration classes
│   │   │   ├── FxDealConfig.java      # Bean configurations
│   │   │   └── OpenApiConfig.java     # API documentation config
│   │   ├── domain/                 # Domain entities
│   │   │   └── FxDeal.java            # FX Deal entity
│   │   ├── dto/                    # Data Transfer Objects
│   │   │   ├── FxDealCreateRequestDTO.java
│   │   │   └── FxDealResponseDTO.java
│   │   ├── exception/              # Custom exceptions
│   │   │   └── BusinessViolationException.java
│   │   ├── mapper/                 # Object mappers
│   │   │   ├── FxDealMapper.java      # Mapper interface
│   │   │   └── FxDealMapperImpl.java  # Mapper implementation
│   │   ├── repository/             # Data access layer
│   │   │   └── FxDealRepository.java
│   │   ├── service/                # Business logic
│   │   │   ├── FxDealService.java
│   │   │   ├── FxDealValidationService.java
│   │   │   ├── CurrencyProvider.java
│   │   │   └── impl/               # Service implementations
│   │   │       ├── FxDealServiceImpl.java
│   │   │       ├── FxDealValidationServiceImpl.java
│   │   │       └── SimpleCurrencyProvider.java
│   │   ├── web/                    # REST controllers
│   │   │   └── FxDealController.java
│   │   └── ClusteredDataWarehouseApplication.java
│   └── resources/
│       ├── application.properties
│       └── db/changelog/           # Liquibase migrations
└── test/                          # Test files
    └── java/com/progressoft/clustereddatawarehouse/
        ├── service/impl/
        │   └── FxDealServiceImplTest.java
        └── ClusteredDataWarehouseApplicationTests.java
```

---

## 🚀 Getting Started

### **Prerequisites**
- **JDK 21** or higher
- **Docker** and **Docker Compose**
- **Maven 3.8+**

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/Mr-AXEL01/Clustered-Data-Warehouse.git
   cd Clustered-Data-Warehouse
   ```

2. **Start the application**
   ```bash
   make up
   ```

   This will:
   - Build the Docker image
   - Start PostgreSQL database
   - Start the Spring Boot application
   - Application available at: `http://localhost:8080`

### **Using Makefile Commands**

The project includes a Makefile with the following commands:

```bash
make up      # Start Docker containers
make down    # Stop Docker containers  
make test    # Run tests
make clean   # Clean build files
```

### **Manual Setup**

If you prefer to run without Docker:

1. **Start PostgreSQL**
   ```bash
   # Using Docker
   docker run -d --name postgres \
     -e POSTGRES_DB=fx_warehouse \
     -e POSTGRES_USER=fx_user \
     -e POSTGRES_PASSWORD=fx_password \
     -p 5432:5432 postgres:16-alpine
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

---

## 📊 API Documentation

### **Swagger UI**
Access the interactive API documentation at: `http://localhost:9090/swagger-ui.html`

### **Deal Import Endpoint**

**Import Single FX Deal**
```http
POST /api/v1/fx-deals
Content-Type: application/json
```

**Request Body:**
```json
{
  "id": "DR123456",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-01T10:00:00Z",
  "amount": 100000.00
}
```

**Success Response (201 Created):**
```json
{
  "id": "DR123456",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-01T10:00:00Z",
  "amount": 100000.00
}
```

**Error Responses:**
- `400 Bad Request` - Invalid request data
- `409 Conflict` - Deal ID already exists

### **Validation Rules**

| Field | Rules |
|-------|-------|
| `id` | Required, must be unique |
| `fromCurrency` | Required, valid ISO 4217 code (3 letters) |
| `toCurrency` | Required, valid ISO 4217 code, different from fromCurrency |
| `amount` | Required, positive number |
| `timestamp` | Required, valid ISO-8601 format, not in future |

---

## ⚙️ Configuration

### **Application Properties**

The application uses the following key configurations:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/fx_warehouse
spring.datasource.username=fx_user
spring.datasource.password=fx_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Liquibase
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

# Server Configuration
server.port=8081

# Actuator
management.endpoints.web.exposure.include=health,info
```

### **Environment Variables**

For Docker deployment, set these environment variables:

```bash
POSTGRES_DB=fx_warehouse
DB_USERNAME=fx_user
DB_PASSWORD=fx_password
```

---

## 🧪 Testing

### **Run Tests**
```bash
# Using Maven
mvn test

# Using Makefile
make test
```

### **Test Structure**
- **Unit Tests**: Service layer logic
- **Integration Tests**: Database interactions
- **Controller Tests**: REST API endpoints

### **Test Coverage**
The project maintains comprehensive test coverage focusing on:
- Business logic validation
- Error handling scenarios
- Data persistence
- API contract testing

---

## 👥 Development

### **Code Quality Standards**
- **Java 21** features and best practices
- **Spring Boot** conventions
- **Clean Architecture** principles
- **SOLID** design principles
- **Comprehensive logging** and error handling

### **Contributing**
1. Fork the repository
2. Create a feature branch
3. Implement changes with tests
4. Run the test suite
5. Submit a pull request

### **Architecture Decisions**
- **No rollback policy**: Each deal processed independently
- **Fail-fast validation**: Early validation prevents invalid data
- **Immutable DTOs**: Using Java records for data transfer
- **Clean separation**: Clear boundaries between layers

---

## 📊 Monitoring

### **Health Check**
```http
GET /actuator/health
```

### **Application Info**  
```http
GET /actuator/info
```

### **Logging**
The application provides structured logging for:
- Deal import operations
- Validation failures
- Database operations
- Error scenarios

---

## 👨‍💻 Author

**ABD-ELHAQ AZROUR (@Mr-AXEL01)**
- Email: abdelhakazrour3@gmail.com
- GitHub: [@Mr-AXEL01](https://github.com/Mr-AXEL01)

---
