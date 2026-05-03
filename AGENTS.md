# SmartLMS AI Agent Guidelines

Concise, project-specific guidance for working in the `smartLMS` codebase.

## Architecture and Data Flow
- Spring Boot REST API with layered packages: `smartlms.controller` → `smartlms.service` → `smartlms.repository` → `smartlms.entity`.
- DTOs live in `smartlms.dto.request` and `smartlms.dto.response` (e.g., `AuthController` uses `StudentCreateDto` and `ApiResponse`).
- Mapping helpers live in `smartlms.mapper` (see package for entity↔DTO mapping patterns).

## Security and Auth
- JWT auth is implemented in `smartlms.jwt.JwtFilter` and wired in `smartlms.configuration.SecurityConfig`.
- Endpoints allowed without auth are declared in both `SecurityConfig` (request matchers) and `JwtFilter` (early allow-list).
- Swagger security scheme is defined in `smartlms.configuration.SwaggerConfiguration` (bearer JWT).

## Response and Error Conventions
- All API responses are wrapped in `smartlms.dto.response.ApiResponse` with `success/message/data/statusCode`.
- Errors are normalized in `smartlms.exception.GlobalExceptionHandler` (e.g., validation and `UnauthorizedException`).

## Persistence
- PostgreSQL is the backing store; schema is in `postgresql_ddl.sql`.
- JPA entities live in `smartlms.entity`, repositories in `smartlms.repository`.

## Config and Profiles
- Active profile is set in `src/main/resources/application.properties` (`spring.profiles.active=dev`).
- Environment-specific settings live in `application-dev.properties` and `application-prod.properties`.

## Developer Workflows
- Build: `mvn clean install`
- Run: `mvn spring-boot:run`
- Tests: `mvn test`

## Key Files
- `src/main/java/smartlms/SmartLmsApplication.java`
- `src/main/java/smartlms/controller/AuthController.java`
- `src/main/java/smartlms/configuration/SecurityConfig.java`
- 
- `src/main/java/smartlms/jwt/JwtFilter.java`
- `src/main/java/smartlms/exception/GlobalExceptionHandler.java`
- `src/main/java/smartlms/dto/response/ApiResponse.java`
