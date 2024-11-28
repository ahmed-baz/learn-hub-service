# Learn Hub Service

This is a **Spring Boot** application that demonstrates how to build a RESTful service with **CRUD** operations, using
the power of **Spring Security** for authentication and authorization in the first phase of the project and using
**Keycloak** as oAuth server in the second phase , and
use **PostgreSQL** for data persistence.

The service manages a basic `User` with `Role` one of Student, Instructor, or an Admin, and `Course` entity with fields
such as `title`, `description`,`start date`, and more, exposing the following CRUD APIs:

- **Login**: to login and get JWT token.
- **Register**: to register as a student.
- **Activate Account**: to activate user account after registration.
- **Create Course**: Add new course.
- **Read Course**: Retrieve course (all or by ID).
- **Update Course**: Modify existing course.
- **Delete Course**: Remove course.
- **Register/Unregister Course**: to register/unregister a course.
- **export course**: to export course schedule.

## Features

### PHASE-1

- Using **Spring Security** for authentication and authorization
- Using **JWT token**
- **CRUD operations** using Spring boot
- **PostgreSQL** as the database
- Using **liquibase**  for database migration
- Using **spring cache**
- Using **OpenAPI** for API documentation
- Enabling **database auditing**
- Using **Lombok** for code generation
- Using **Mapstruct** for object mapping
- Using **Docker** for containerization

### PHASE-2

- Upgrading the service to use **Keycloak** as oAuth server
- Adding more APIs related to course rating and instructors search
- Do integration with **prometheus** and **zipkin** for monitoring

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17+** (Recommended)
- **Maven 3.8+**
- **PostgreSQL** (either locally or via Docker)
- **Docker** (optional, for PostgreSQL container)

## Setup Instructions

#### Follow these steps to get the service running:

```bash
git clone https://github.com/ahmed-baz/learn-hub-service.git
cd learn-hub-service
docker compose up -d
