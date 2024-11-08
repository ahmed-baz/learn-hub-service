# Learn Hub Service

This is a **Spring Boot** application that demonstrates how to build a RESTful service with **CRUD** operations, using
the power of **Spring Security** for authentication and authorization, and
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

- Using **JWT token** and configure spring security
- **CRUD operations** using Spring boot.
- **PostgreSQL** as the database.
- Using **liquibase**  for database migration.
- Using **spring cache**.
- Enabling **database auditing**.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17+** (Recommended)
- **Maven 3.8+**
- **PostgreSQL** (either locally or via Docker)
- **Docker** (optional, for PostgreSQL container)

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/ahmed-baz/learn-hub-service.git
cd learn-hub-service
