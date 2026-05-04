# SmartLMS AI Agent Guidelines

Concise, project-specific guidance for working in the `smartLMS` codebase.

## Architecture and Data Flow
- Spring Boot 4.0.5 REST API with layered packages: `smartlms.controller` → `smartlms.service` → `smartlms.repository` → `smartlms.entity`.
- Services use interface-implementation pattern: interfaces in `smartlms.service`, implementations in `smartlms.service.serviceImpl` (both use @Service annotation).
- DTOs live in `smartlms.dto.request` and `smartlms.dto.response` (e.g., `AuthController` uses `StudentCreateDto` and `ApiResponse`).
- Paginated responses use `smartlms.dto.response.PageResponse<T>`.
- DTO projections (for custom queries) live in `smartlms.dto.projection` (e.g., `AssigmentProjectionForStudents`, `GroupSubjectProjectionForTeachers`).
- Mappers are @Component-annotated beans in `smartlms.mapper` that handle entity↔DTO conversions (e.g., `UserMapper`, `SubjectMapper`, `StudentProfileMapper`).

## Security and Auth
- JWT auth is implemented in `smartlms.jwt.JwtFilter` and wired in `smartlms.configuration.SecurityConfig`.
- User details loading via `smartlms.service.security.CustomUserDetailsService` for Spring Security.
- Endpoints allowed without auth are declared in both `SecurityConfig` (request matchers) and `JwtFilter` (early allow-list).
- Swagger security scheme is defined in `smartlms.configuration.SwaggerConfiguration` (bearer JWT).
- Access denied handling via `smartlms.exception.CustomAccessDeniedHandler`.

## Response and Error Conventions
- All API responses are wrapped in `smartlms.dto.response.ApiResponse` with `success/message/data/statusCode`.
- Errors are normalized in `smartlms.exception.GlobalExceptionHandler` (e.g., validation, `UnauthorizedException`, `DataNotFoundException`, `AlreadyExistsException`, `ScoreExceededException`).

## Persistence
- PostgreSQL is the backing store; schema is in `postgresql_ddl.sql`.
- JPA entities live in `smartlms.entity` with `BaseEntity` as the abstract superclass (provides `id: UUID`, `createdAt`, `updatedAt` with auto-management).
- Entity enums in `smartlms.entity.enums` (e.g., `UserRole`, `UserStatus`, `Gender`, `AttendanceStatus`, `AssignmentType`, `SubmissionStatus`, `LessonType`, `WeekDay`, `MessageRole`).
- Repositories in `smartlms.repository` (all marked with @Repository).

## Config and Profiles
- Active profile is set in `src/main/resources/application.properties` (`spring.profiles.active=dev` by default).
- Environment-specific settings live in `application-dev.properties` and `application-prod.properties`.
- Admin initialization via `smartlms.configuration.AdminDataLoader` (runs on startup if `spring.sql.init.mode=always`; default credentials: `username=admin`, `password=admin123`).

## Developer Workflows
- Build: `mvn clean install`
- Run: `mvn spring-boot:run`
- Tests: `mvn test`

## Key Files
- `src/main/java/smartlms/SmartLmsApplication.java`
- `src/main/java/smartlms/controller/AuthController.java`
- `src/main/java/smartlms/configuration/SecurityConfig.java`
- `src/main/java/smartlms/configuration/AdminDataLoader.java`
- `src/main/java/smartlms/jwt/JwtFilter.java`
- `src/main/java/smartlms/exception/GlobalExceptionHandler.java`
- `src/main/java/smartlms/dto/response/ApiResponse.java`
