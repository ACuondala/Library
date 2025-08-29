# Library Management System

A modern Spring Boot-based REST API for managing library resources including books, authors, publishers, and categories.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Docker Support](#docker-support)
- [Contributing](#contributing)

## 🎯 Overview

The Library Management System is a backend application designed to manage library resources efficiently. It provides a RESTful API for managing books, authors, publishers, and categories with support for complex many-to-many relationships.

### Key Benefits

- **Centralized Management**: Single source of truth for all library entities
- **Flexible Relationships**: Support for many-to-many relationships between books and related entities
- **Data Integrity**: Comprehensive validation and error handling
- **Scalable Architecture**: Clean separation of concerns with layered architecture
- **API Documentation**: Built-in Swagger/OpenAPI documentation

## ✨ Features

### Core Functionality

- **📚 Book Management**: Complete CRUD operations for books
- **👤 Author Management**: Manage author information and relationships
- **🏢 Publisher Management**: Handle publisher data and associations
- **📂 Category Management**: Organize books by categories with filtering support
- **🔍 Advanced Search**: Filter and search through categories with specifications
- **📊 Excel Integration**: Import/export capabilities using Apache POI

### Technical Features

- **RESTful API**: Clean and intuitive REST endpoints
- **Data Validation**: Comprehensive input validation using Jakarta Validation
- **Error Handling**: Structured error responses with global exception handling
- **Transaction Management**: ACID transactions for data consistency
- **Database Flexibility**: Support for multiple databases (H2, PostgreSQL, MySQL, MSSQL)
- **API Documentation**: Auto-generated Swagger documentation

## 🛠 Technology Stack

### Backend Framework
- **Spring Boot 3.2.4** - Main application framework
- **Java 17** - Programming language
- **Spring Data JPA** - Data persistence and ORM
- **Spring Web** - REST API development
- **Spring Validation** - Input validation

### Database
- **PostgreSQL** - Primary database (production)
- **H2** - In-memory database (testing)
- **MySQL/MSSQL** - Alternative database support

### Mapping & Validation
- **MapStruct 1.5.5** - DTO-Entity mapping
- **Jakarta Validation API** - Bean validation
- **Hibernate** - ORM implementation

### Documentation & Tools
- **Springdoc OpenAPI** - API documentation
- **Apache POI 5.2.3** - Excel processing
- **Lombok** - Code generation (optional)
- **Maven** - Build tool

### Testing
- **JUnit 5** - Unit testing framework
- **Spring Boot Test** - Integration testing
- **AssertJ** - Fluent assertions
- **Mockito** - Mocking framework

## 🏗 Architecture

The application follows a layered architecture pattern:

```
┌─────────────────┐
│   Controllers   │ ← REST API Layer
├─────────────────┤
│    Services     │ ← Business Logic Layer
├─────────────────┤
│  Repositories   │ ← Data Access Layer
├─────────────────┤
│    Entities     │ ← Domain Model Layer
└─────────────────┘
```

### Key Components

- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic and orchestrate operations
- **Repositories**: Data access using Spring Data JPA
- **DTOs**: Data transfer objects for API communication
- **Mappers**: Convert between DTOs and entities using MapStruct
- **Entities**: JPA entities representing domain models

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **PostgreSQL 12+** (for production)
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd library
   ```

2. **Configure the database**
   
   Create a PostgreSQL database and update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/library
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Quick Test

Visit `http://localhost:8080/swagger-ui.html` to access the API documentation and test endpoints.

## 📖 API Documentation

### Base URL
```
http://localhost:8080
```

### Main Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/books` | Get all books (paginated) |
| GET | `/books/{id}` | Get book by ID |
| POST | `/books` | Create a new book |
| PUT | `/books/{id}` | Update an existing book |
| DELETE | `/books/{id}` | Delete a book |
| GET | `/authors` | Get all authors |
| GET | `/publishers` | Get all publishers |
| GET | `/categories` | Get all categories (with filtering) |

### Example: Create a Book

```json
POST /books
Content-Type: application/json

{
  "isbn": "978-0-439-70818-8",
  "title": "Harry Potter and the Philosopher's Stone",
  "authorNames": ["J.K. Rowling"],
  "publisherNames": ["Bloomsbury"],
  "categoryNames": ["Fantasy", "Young Adult"],
  "publishDate": "1997-06-26",
  "formate": "Hardcover",
  "numberOfPage": 223,
  "qtd": 100,
  "qtdAvailable": 95
}
```

### Response Format

All API responses follow this structure:
```json
{
  "message": "Success message",
  "statusCode": 200,
  "data": { /* response data */ }
}
```

## 🗄 Database Schema

### Core Tables

- **books** - Main book information
- **authors** - Author details
- **publishers** - Publisher information
- **categories** - Book categories

### Relationship Tables (Many-to-Many)

- **author_books** - Books ↔ Authors
- **book_publisher** - Books ↔ Publishers  
- **book_category** - Books ↔ Categories

### Key Features

- **UUID Primary Keys** - All entities use UUID for better scalability
- **Audit Fields** - Created timestamps for all entities
- **Cascade Operations** - Proper cascade configuration for relationships
- **Data Integrity** - Foreign key constraints and validation

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=BooksServiceTest
```

### Test Categories

- **Unit Tests**: Service layer logic testing
- **Integration Tests**: End-to-end functionality testing
- **Repository Tests**: Data access layer testing

### Test Configuration

Tests use H2 in-memory database configured in `application-test.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:testDb
spring.jpa.hibernate.ddl-auto=create-drop
```

## 🐳 Docker Support

### Build Docker Image
```bash
docker build -t library-app .
```

### Run with Docker
```bash
docker run -d -p 8080:8080 library-app
```

### Docker Compose
```bash
docker-compose up -d
```

## 📁 Project Structure

```
library/
├── src/
│   ├── main/
│   │   ├── java/com/example/nada/
│   │   │   ├── Controllers/          # REST controllers
│   │   │   ├── Services/             # Business logic
│   │   │   ├── Repositories/         # Data access
│   │   │   ├── Models/               # JPA entities
│   │   │   ├── Dtos/                 # Data transfer objects
│   │   │   ├── Mappers/              # MapStruct mappers
│   │   │   ├── Exceptions/           # Custom exceptions
│   │   │   ├── Helpers/              # Utility classes
│   │   │   ├── Wrappers/             # Response wrappers
│   │   │   ├── Specification/        # JPA specifications
│   │   │   └── configs/              # Configuration classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── messages.properties
│   └── test/
│       ├── java/                     # Test classes
│       └── resources/
│           └── application-test.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java coding conventions
- Write tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔧 Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database URL | `jdbc:postgresql://localhost:5432/library` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `1822` |

### Application Properties

Key configuration options in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/library}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:1822}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# Internationalization
spring.messages.basename=messages
spring.messages.encoding=UTF-8
```

## 📞 Support

For support and questions:

- Create an issue in the GitHub repository
- Check the API documentation at `/swagger-ui.html`
- Review the test cases for usage examples

---

**Built with ❤️ using Spring Boot and Java 17**