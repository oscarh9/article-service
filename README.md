# FIFE - Article Service

FIFE is a personal project that simulates a small sports newspaper inspired by MARCA.
This microservice is the Article Service, responsible for the complete management of sports articles: creation, retrieval, updating, and deletion.

## Table of contents

- [Main Technologies](#main-technologies)
- [Architecture & Structure](#architecture--structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [CI/CD](#cicd)
- [Contributors](#contributors)
- [Contact](#contact)
---
## Main Technologies

- Java 21
- Spring Boot 3.2.5
- MongoDB (Docker container)
- Maven (with Spotless and Jacoco plugins)
- SpringDoc OpenAPI (Swagger)
- Testing with JUnit 5 and Mockito
- CI/CD with GitHub Actions and SonarCloud for analysis and coverage
- Microservices-based architecture (this is the first one)
- Lombok for boilerplate reduction
- ModelMapper for DTO ↔ Model ↔ Entity mapping

## Architecture and structure

The application follows a layered pattern:

- **Controller**: exposes REST APIs and works with **DTOs** (maps to domain **Model**).
- **Service**: business logic.
- **DAO**: maps **Model** ↔ **Entity** and delegates to **Repository**.
- **Repository**: persistence layer (MongoDB).

Main folders:

- `config`
- `controller`
- `dao`
- `dto`
- `entity`
- `exception`
- `model`
- `properties`
- `repository`
- `service`
- `utils`

## Prerequisites

- Java 21 installed
- Docker running for the MongoDB container
- IntelliJ (or your IDE) configured with environment variables for MongoDB

## Getting Started

1. Clone the repository:
   ```bash
    git clone https://github.com/oscarh9/article-service.git
    cd article-service
   ```
   
2. Start MongoDB with Docker:
   ```bash
    docker run -d -p 27017:27017 --name article_db mongo:latest
   ```

3. Run the service:
   ```bash
    mvn spring-boot:run
   ```

4. Access Swagger UI to explore the API:
   ```bash
    http://localhost:8080/swagger-ui.html
   ```

## Configuration

Use environment variables in your Run/Debug configuration (recommended).


## API Documentation

- Generated with SpringDoc OpenAPI.
- Browse at: http://localhost:8080/swagger-ui.html

## Testing

- **Unit tests**: Service and DAO layers
- **Integration tests**: Controller layer

Run tests:
   ```bash
   mvn test
   ``` 

## CI/CD

- **GitHub Actions**: runs on Pull Request
    - Build & test
    - SonarCloud quality/coverage analysis
    - Spotless formatting check
---
## Contributors

- **Oscar** - main developer
- **Pol** - code reviews and guidance

## Contact

- **Oscar Herencia** - oscarherenciasakkis@gmail.com