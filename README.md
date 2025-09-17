# DuyPhong App

A Spring Boot application for company management with employee, department, and task tracking features.

## Prerequisites

Before running this project, make sure you have the following installed:

- **Java 17**
- **Maven 3.6+**
- **Docker and Docker Compose** (for database)
- **Git** (to clone the repository)

## Installation and Setup

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
mvn clean compile
```

#### Run the application:
```bash
mvn spring-boot:run
```

Alternatively, you can build a JAR file and run it:
```bash
mvn clean package
java -jar target/duyphong-app-0.0.1-SNAPSHOT.jar
```

### 5. Access the Application

Once the application starts successfully:

- **Application URL**: `http://localhost:8080`
- **Swagger UI (API Documentation)**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

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

### Running Tests
```bash
mvn test
```

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
mvn clean install

# Skip tests if needed
mvn clean install -DskipTests
```

## Project Structure

```
duyphong-app/
├── src/
│   ├── main/
│   │   ├── java/com/duyphong/duyphong_app/
│   │   │   ├── controller/     # REST Controllers
│   │   │   ├── service/        # Business Logic
│   │   │   ├── repository/     # Data Access Layer
│   │   │   ├── entity/         # JPA Entities
│   │   │   ├── dto/           # Data Transfer Objects
│   │   │   └── config/        # Configuration Classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/                  # Test classes
├── container/
│   ├── docker-compose.yml     # Docker configuration
│   ├── dump-company-*.sql     # Database schema
│   └── insertdata.sql         # Sample data
├── target/                    # Build output
├── pom.xml                   # Maven configuration
└── README.md                 # This file
```
