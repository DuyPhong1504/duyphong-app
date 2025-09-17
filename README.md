# DuyPhong App

A Spring Boot application for company management with employee, department, and task tracking features.

## Prerequisites

Before running this project, make sure you have the following installed:

- **Java 17**
- **Maven 3.6+**
- **Docker and Docker Compose** (for database)
- **Git** (to clone the repository)

## Installation and Setup

### 0. Open Terminal/Command Prompt

Before running any commands, you need to open a terminal or command prompt:

**On Windows:**

- Press `Win + R`, type `cmd` and press Enter
- Or press `Win + X` and select "Command Prompt" or "PowerShell"
- Or open VS Code and use the integrated terminal (`Ctrl + ``)

**On macOS/Linux:**

- Press `Ctrl + Alt + T` (Linux)
- Press `Cmd + Space`, type "Terminal" and press Enter (macOS)
- Or use the integrated terminal in VS Code

### 1. Clone the Repository

```bash
git clone https://github.com/DuyPhong1504/duyphong-app.git
cd duyphong-app
```

### 2. Set up MySQL Database with Docker

The project includes a Docker Compose configuration to easily set up a MySQL database with sample data.

#### Navigate to the container directory:

```bash
cd container
```

#### Start the MySQL container:

```bash
docker-compose up -d
```

This will:

- Pull MySQL 8.0 image (if not already available)
- Create a MySQL container with database name `company`
- Automatically execute the SQL files to set up tables and insert sample data
- Expose MySQL on port 3306

#### Verify the database is running:

```bash
docker-compose ps
```

You should see the MySQL container in "Up" status.

### 3. Configure Application Properties

The application is already configured to connect to the Docker MySQL instance. You can verify the database configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/company?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Build and Run the Application

#### For Production (using MySQL):

#### Navigate back to the project root folder:

If you are currently in the `container` directory, navigate back to the project root:

```bash
cd ..
```

If you are already in the project root folder, you can skip this step.

#### Build the project:

```bash
./mvnw clean compile
```

#### Run the application:

```bash
./mvnw spring-boot:run
```

Alternatively, you can build a JAR file and run it:

```bash
./mvnw clean package
java -jar target/duyphong-app-0.0.1-SNAPSHOT.jar
```

#### For Development (using H2 in-memory database):

For quick development and testing, you can use the H2 in-memory database instead of MySQL:

#### Build and run with development profile:

```bash
./mvnw spring-boot:run -Pdev
```

Or using Spring profiles:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

This will:

- Use H2 in-memory database instead of MySQL
- Automatically create tables and load sample data
- Enable H2 console at `http://localhost:8080/h2-console`
- Set enhanced logging for development

#### H2 Console Access:

When running in development mode, you can access the H2 database console at:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:company`
- **Username**: `sa`
- **Password**: (leave empty)

### 5. Access the Application

Once the application starts successfully:

- **Application URL**: `http://localhost:8080`
- **Swagger UI (API Documentation)**: `http://localhost:8080/swagger-ui/index.html`

## Available Endpoints

The application provides REST APIs for:

- **Departments**: Manage company departments
- **Employees**: Manage employee information
- **Tasks**: Manage tasks and assignments

You can explore all available endpoints using the Swagger UI interface.

## API Testing

For comprehensive API testing documentation with sample requests and responses, see [API_TEST_CASES.md](API_TEST_CASES.md).

This document includes:

- Detailed test cases for all endpoints
- Sample request/response data
- Error handling examples
- Database setup instructions
- Testing tips and best practices

## Database Information

The application supports two database configurations:

### Production Environment (MySQL)

- Uses MySQL 8.0 with Docker
- Requires Docker Compose setup (see Installation section)
- Full dataset with 100 departments, 100 employees, and complete sample data
- Persistent data storage

### Development Environment (H2)

- Uses H2 in-memory database
- No Docker required - quick startup
- Sample dataset with 20 departments, 20 employees, and basic sample data
- Data resets on application restart
- Includes H2 web console for database inspection

### Switching Between Environments

#### Run with MySQL (Production):

```bash
./mvnw spring-boot:run
```

#### Run with H2 (Development):

```bash
./mvnw spring-boot:run -Pdev
```

### Database Configuration Files

The application uses different configuration files for each environment:

- **`application.properties`** - Default MySQL configuration
- **`application-dev.properties`** - H2 development configuration
- **`schema.sql`** - H2 database schema (automatically executed in dev mode)
- **`data.sql`** - H2 sample data (automatically loaded in dev mode)

The application uses MySQL with the following tables and structure:

### Main Tables

- **`departments`** - Company departments

  - Contains 100+ departments with IDs like `dept-001`, `dept-002`, etc.
  - Includes various departments: Sales, Marketing, Engineering, Finance, HR, IT, Legal, etc.

- **`employees`** - Employee information

  - Contains 100 employees with IDs from `emp-001` to `emp-100`
  - Fields: id, username, email, fullname, department, position, salary, created_at, updated_at
  - Sample employees include developers, managers, analysts, specialists across all departments

- **`tasks`** - Task management

  - Task assignments and tracking system
  - Links tasks to employees with status tracking

- **`lunch_logs`** - Meal tracking system

  - Records employee meal information
  - Fields: id, employee_id, lunch_date, meal_type, restaurant, notes
  - Tracks lunch and dinner meals for employees

- **`department_history`** - Department change tracking

  - Tracks employee department transfers over time
  - Contains 101 records showing historical department changes
  - Fields: id, employee_id, old_department_id, new_department_id, change_date

- **`employee_department`** - Employee-Department relationship table
  - Junction table for employee-department associations

### Sample Data Overview

The database comes pre-loaded with:

- **100 departments** (dept-001 to dept-100) covering all major business functions
- **100 employees** (emp-001 to emp-100) with realistic profiles and salaries
- **100+ department history records** showing employee movements between departments
- **Lunch log entries** for meal tracking functionality

Sample data is automatically loaded when the Docker container starts using the `dump-company-202509162226.sql` file.

## Development

### Hot Reload

The application is configured with Spring Boot DevTools for development convenience. Changes to Java files will trigger an automatic restart.

## Stopping the Application

### Stop the Spring Boot application:

Press `Ctrl + C` in the terminal where the application is running.

### Stop the MySQL container:

```bash
cd container
docker-compose down
```

### Stop and remove all data (including database volume):

```bash
docker-compose down -v
```

## Troubleshooting

### Database Connection Issues

1. Ensure Docker is running: `docker --version`
2. Check if MySQL container is up: `docker-compose ps`
3. Verify database logs: `docker-compose logs mysql`

### Port Conflicts

- If port 3306 is already in use, modify the port mapping in `container/docker-compose.yml`
- If port 8080 is in use, change `server.port` in `application.properties`

### Maven Build Issues

```bash
# Clean and rebuild
./mvnw clean install

# Skip tests if needed
./mvnw clean install -DskipTests
```

## Project Structure

```
duyphong-app/
├── .github/                   # GitHub workflows and configurations
├── .mvn/                      # Maven wrapper configuration
├── .vscode/                   # VS Code settings
├── container/                 # Docker configuration
│   ├── docker-compose.yml     # Docker Compose setup
│   ├── dump-company-*.sql     # Database schema dump
│   └── insertdata.sql         # Sample data insertion
├── src/
│   ├── main/
│   │   ├── java/com/duyphong/duyphong_app/
│   │   │   ├── common/         # Common utilities and shared code
│   │   │   ├── config/         # Configuration classes (Swagger, etc.)
│   │   │   ├── controller/     # REST API Controllers
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   │   ├── request/    # Request DTOs
│   │   │   │   └── response/   # Response DTOs
│   │   │   ├── entity/         # JPA Entities (Database models)
│   │   │   ├── enumeration/    # Enum classes
│   │   │   ├── exception/      # Custom exceptions and handlers
│   │   │   ├── mapper/         # Entity-DTO mapping classes
│   │   │   ├── repository/     # Data Access Layer (JPA Repositories)
│   │   │   ├── service/        # Business Logic Layer
│   │   │   ├── utils/          # Utility classes
│   │   │   ├── validation/     # Custom validation classes
│   │   │   └── DuyphongAppApplication.java  # Main Spring Boot class
│   │   └── resources/
│   │       ├── static/         # Static web resources
│   │       ├── templates/      # Template files (if using template engine)
│   │       └── application.properties  # Application configuration
│   └── test/                  # Test classes
├── target/                    # Maven build output
├── .gitignore                 # Git ignore rules
├── mvnw                       # Maven wrapper script (Unix/Mac)
├── mvnw.cmd                   # Maven wrapper script (Windows)
├── pom.xml                   # Maven project configuration
├── HELP.md                   # Spring Boot help documentation
└── README.md                 # This file
```
