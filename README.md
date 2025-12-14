# Fleet & Logistics Management System 🚚
A microservice-based logistics management system built with Java, Spring Boot, PostgreSQL, Hibernate, and JavaFX.

## Auth Service
This service handles *authentication and user management* with the following features implemented so far:

**User Model:**
Includes username, email, first name, last name, phone number, date of birth, hashed password using BCrypt, roles (e.g., ROLE_ADMIN, ROLE_USER), and audit fields like createdAt, updatedAt, lastLogin.

**Authentication:**
- Login endpoint: POST `/api/auth/login` that authenticates users using Spring Security’s AuthenticationManager.
- Returns JWT containing username and roles.
- JWT validation via JwtAuthenticationFilter.
- Refresh token support:
  - Generates and stores refresh tokens tied to users with expiration.
  - Endpoint POST `/api/auth/refresh` validates and renews access tokens using refresh tokens.
  - Supports secure session continuation without re-login.

**User Management API:**
- `GET /api/users` — returns a list of all users.
- `GET /api/users/{username}` — returns details of a single user.
- `POST /api/users` — creates new users with passwords hashed using BCrypt.
- `PUT /api/users/{username}` — updates user details, including roles.
- `DELETE /api/users/{username}` — deletes users by username.
- Endpoints secured by role-based access control (hasRole("ADMIN") for write/delete operations).

**Security:**
- Passwords hashed with BCrypt to protect against brute force attacks.
- JWT tokens used for stateless authentication with role-based claims.
- Security configuration permits unauthenticated access to `/api/auth/login` and `/api/auth/refresh` to protects other endpoints.
- `LastLogin` field updated upon successful login.

## Fleet Service
This service handles *vehicle data management* with the following features implemented so far:

**Vehicle Model:**
Includes `vin`, `plate`, `type` (TRUCK, VAN, TRAILER), `capacityKg`, `status` (AVAILABLE, ASSIGNED, MAINTENANCE), `lastMaintenance`, `mileage`, `lat`, `lon`.

**Persistence Layer:**
- `VehicleRepository` extending `JpaRepository<Vehicle, UUID>`.
- Query methods: `findByStatus`, `findByType`.
