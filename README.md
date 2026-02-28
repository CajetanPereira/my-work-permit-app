# Work Permit Platform (Spring Boot Microservices)

A microservices-based platform to generate and manage work permits for vendors in specific locations.

## Architecture

- **API Gateway (`api-gateway`)**
  - Single entry point for clients
  - Routes traffic to downstream services
  - Validates JWT tokens before forwarding requests
- **Auth Service (`auth-service`)**
  - User management
  - Authentication (`/auth/login`)
  - Registration (`/auth/register`)
  - Role-based authorization for admin-only user operations
  - Persists user data in **PostgreSQL**
- **Permit Service (`permit-service`)**
  - Create, retrieve, list, and approve/reject permits
  - Role-based authorization:
    - `VENDOR` and `ADMIN` can create permits
    - `ADMIN` and `SECURITY` can approve/reject permits
  - Persists permits in **MongoDB**
  - Uses **Redis** for caching permit reads/list queries

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Cloud Gateway
- PostgreSQL (auth and users)
- MongoDB (work permits)
- Redis (caching)
- Docker Compose for local orchestration

## Project Structure

- `api-gateway/`
- `auth-service/`
- `permit-service/`
- `docker-compose.yml`

## Run Locally

```bash
docker compose up --build
```

Services:

- Gateway: `http://localhost:8080`
- Auth Service: `http://localhost:8081`
- Permit Service: `http://localhost:8082`

## Core APIs (through gateway)

### Auth

- `POST /auth/register`
- `POST /auth/login`
- `GET /auth/me`
- `GET /auth/users` (ADMIN)
- `PUT /auth/users/{id}/roles` (ADMIN)

### Permits

- `POST /permits`
- `GET /permits/{id}`
- `GET /permits?location={location}`
- `PUT /permits/{id}/status`

## JWT and Roles

JWT contains:

- `sub`: username
- `roles`: comma-separated roles (`ADMIN,VENDOR,SECURITY`)

All services share the same `JWT_SECRET`.
