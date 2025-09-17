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

### 5. Access the Application

Once the application starts successfully:

- **Application URL**: `http://localhost:8080`
- **Swagger UI (API Documentation)**: `http://localhost:8080/swagger-ui.html`

## Available Endpoints

The application provides REST APIs for:

- **Departments**: Manage company departments
- **Employees**: Manage employee information
- **Tasks**: Manage tasks and assignments

You can explore all available endpoints using the Swagger UI interface.

## Database Information

The application uses MySQL with the following tables:

- `departments` - Company departments
- `employees` - Employee information
- `tasks` - Task management
- `department_history` - Department change tracking

Sample data is automatically loaded when the Docker container starts.

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
