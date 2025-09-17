# API Test Cases Documentation

This document contains comprehensive test cases for all implemented APIs in the DuyPhong App. Use these test cases to validate your API endpoints using tools like Postman, cURL, or any HTTP client.

## Base URL

```
http://localhost:8080
```

---

## 1. Employee Management APIs

### 1.1 Get Employee by ID

**Endpoint:** `GET /api/employees/{id}`

#### Test Case 1.1.1: Valid Employee ID

```http
GET /api/employees/emp-001
```

**Expected Response:** `200 OK`

```json
{
  "id": "emp-001",
  "username": "john.doe",
  "email": "john.doe@company.com",
  "fullname": "John Doe",
  "department": "Engineering",
  "position": "Software Engineer",
  "salary": 75000,
  "createdAt": "2025-01-01T00:00:00Z",
  "updatedAt": "2025-01-01T00:00:00Z"
}
```

#### Test Case 1.1.2: Non-existent Employee ID

```http
GET /api/employees/INVALID_ID
```

**Expected Response:** `404 Not Found`

#### Test Case 1.1.3: Empty Employee ID

```http
GET /api/employees/
```

**Expected Response:** `404 Not Found` (Route not found)

---

### 1.2 Get Employee Detail with Department and Tasks

**Endpoint:** `GET /api/employees/detail/{id}`

#### Test Case 1.2.1: Valid Employee ID with Ongoing Tasks

```http
GET /api/employees/detail/emp-001
```

**Expected Response:** `200 OK`

```json
{
  "id": "emp-001",
  "fullname": "John Doe",
  "email": "john.doe@company.com",
  "position": "Software Engineer",
  "salary": 75000,
  "department": {
    "id": "DEPT001",
    "name": "Engineering"
  },
  "ongoingTasks": [
    {
      "id": 1,
      "taskName": "Implement User Authentication",
      "description": "Add JWT-based authentication system",
      "dueDate": "2025-10-01",
      "status": "IN_PROGRESS"
    },
    {
      "id": 2,
      "taskName": "Code Review",
      "description": "Review pull requests from team",
      "dueDate": "2025-09-25",
      "status": "TO_DO"
    }
  ]
}
```

#### Test Case 1.2.2: Valid Employee ID without Ongoing Tasks

```http
GET /api/employees/detail/emp-001
```

**Expected Response:** `200 OK`

```json
{
  "id": "EMP002",
  "fullname": "Jane Smith",
  "email": "jane.smith@company.com",
  "position": "HR Manager",
  "salary": 65000,
  "department": {
    "id": "DEPT002",
    "name": "Human Resources"
  },
  "ongoingTasks": []
}
```

#### Test Case 1.2.3: Non-existent Employee ID

```http
GET /api/employees/detail/INVALID_ID
```

**Expected Response:** `404 Not Found`

---

### 1.3 Update Employee Information

**Endpoint:** `PUT /api/employees/{id}`

#### Test Case 1.3.1: Update All Fields

```http
PUT /api/employees/emp-001
Content-Type: application/json

{
  "fullname": "John Updated Doe",
  "position": "Senior Software Engineer",
  "salary": 85000
}
```

**Expected Response:** `200 OK`

```json
{
  "id": "emp-001",
  "username": "john.doe",
  "email": "john.doe@company.com",
  "fullname": "John Updated Doe",
  "department": "Engineering",
  "position": "Senior Software Engineer",
  "salary": 85000,
  "createdAt": "2025-01-01T00:00:00Z",
  "updatedAt": "2025-09-17T10:30:00Z"
}
```

#### Test Case 1.3.2: Update Only Name

```http
PUT /api/employees/emp-001
Content-Type: application/json

{
  "fullname": "John Smith"
}
```

**Expected Response:** `200 OK`

```json
{
  "id": "emp-001",
  "username": "j.doe",
  "email": "john.doe@example.com",
  "fullname": "John Smith",
  "department": "Engineering",
  "position": "Senior Software Engineer",
  "salary": 85000,
  "createdAt": "2023-01-15T09:00:00Z",
  "updatedAt": "2025-09-17T13:09:49Z"
}
```

#### Test Case 1.3.3: Update Only Position

```http
PUT /api/employees/emp-001
Content-Type: application/json

{
  "position": "Tech Lead"
}
```

**Expected Response:** `200 OK`

```json
{
  "id": "emp-001",
  "username": "j.doe",
  "email": "john.doe@example.com",
  "fullname": "John Smith",
  "department": "Engineering",
  "position": "Tech Lead",
  "salary": 85000,
  "createdAt": "2023-01-15T09:00:00Z",
  "updatedAt": "2025-09-17T13:10:45Z"
}
```

#### Test Case 1.3.4: Update Only Salary

```http
PUT /api/employees/emp-001
Content-Type: application/json

{
  "salary": 90000
}
```

**Expected Response:** `200 OK`

```json
{
  "id": "emp-001",
  "username": "j.doe",
  "email": "john.doe@example.com",
  "fullname": "John Smith",
  "department": "Engineering",
  "position": "Tech Lead",
  "salary": 90000,
  "createdAt": "2023-01-15T09:00:00Z",
  "updatedAt": "2025-09-17T13:11:41Z"
}
```

#### Test Case 1.3.5: Invalid Salary (Negative)

```http
PUT /api/employees/emp-001
Content-Type: application/json

{
  "salary": -1000
}
```

**Expected Response:** `400 Bad Request`

```json
{
  "timestamp": "2025-09-17T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Salary must be greater than 0"
}
```

#### Test Case 1.3.6: Invalid Name (Too Short)

```http
PUT /api/employees/emp-001
Content-Type: application/json

{
  "fullname": "A"
}
```

**Expected Response:** `400 Bad Request`

#### Test Case 1.3.7: Empty Request Body

```http
PUT /api/employees/emp-001
Content-Type: application/json

{}
```

**Expected Response:** `400 Bad Request`

```json
{
  "timestamp": "2025-09-17T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "At least one field (fullname, position, or salary) must be provided for update"
}
```

#### Test Case 1.3.8: Non-existent Employee

```http
PUT /api/employees/INVALID_ID
Content-Type: application/json

{
  "fullname": "Test Name"
}
```

**Expected Response:** `404 Not Found`

---

### 1.4 Update Employee Department

**Endpoint:** `PUT /api/employees/department/{employeeId}`

#### Test Case 1.4.1: Valid Department Change

```http
PUT /api/employees/department/emp-002
Content-Type: application/json

{
  "newDepartmentId": "dept-005"
}
```

**Expected Response:** `200 OK`

```json
{
  "employeeId": "emp-002",
  "employeeFullname": "Jane Smith",
  "employeeEmail": "jane.smith@example.com",
  "employeePosition": "Marketing Specialist",
  "employeeSalary": 65000,
  "oldDepartmentId": "dept-002",
  "oldDepartmentName": "Marketing",
  "newDepartmentId": "dept-005",
  "newDepartmentName": "Engineering",
  "changeDate": "2025-09-17T13:18:50.820494800Z",
  "message": "Employee department updated successfully"
}
```

#### Test Case 1.4.2: Same Department (Should Fail)

```http
PUT /api/employees/department/emp-002
Content-Type: application/json

{
  "newDepartmentId": "dept-005"
}
```

**Expected Response:** `400 Bad Request`

```json
{
  "timestamp": "2025-09-17T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Employee is already in the specified department"
}
```

#### Test Case 1.4.3: Non-existent Employee

```http
PUT /api/employees/department/INVALID_ID
Content-Type: application/json

{
  "newDepartmentId": "DEPT002"
}
```

**Expected Response:** `404 Not Found`

#### Test Case 1.4.4: Non-existent Department

```http
PUT /api/employees/department/emp-001
Content-Type: application/json

{
  "newDepartmentId": "INVALID_DEPT"
}
```

**Expected Response:** `404 Not Found`

---

## 2. Task Management APIs

### 2.1 Create New Task

**Endpoint:** `POST /api/tasks`

#### Test Case 2.1.1: Valid Task Creation

```http
POST /api/tasks
Content-Type: application/json

{
  "employeeId": "emp-001",
  "taskName": "Implement Payment Gateway",
  "description": "Integrate Stripe payment system for online payments",
  "dueDate": "2025-10-17"
}
```

**Expected Response:** `201 Created`

```json
{
  "id": 10,
  "taskName": "Implement Payment Gateway",
  "description": "Integrate Stripe payment system for online payments",
  "status": "TO_DO",
  "dueDate": null,
  "createdAt": "2025-09-17T10:30:00Z",
  "updatedAt": "2025-09-17T10:30:00Z",
  "employee": {
    "id": "emp-001",
    "fullname": "John Doe",
    "email": "john.doe@company.com"
  }
}
```

#### Test Case 2.1.2: Missing Employee ID

```http
POST /api/tasks
Content-Type: application/json

{
  "taskName": "Test Task",
  "description": "Test Description"
}
```

**Expected Response:** `400 Bad Request`

```json
{
  "timestamp": "2025-09-17T20:23:09.2684951",
  "status": 400,
  "error": "Validation Failed",
  "message": "Request validation failed",
  "fieldErrors": {
    "dueDate": "Due date cannot be null. Please use format: yyyy-MM-dd (e.g., 2025-12-31)",
    "employeeId": "Employee ID cannot be blank"
  }
}
```

#### Test Case 2.1.3: Missing Task Name

```http
POST /api/tasks
Content-Type: application/json

{
  "employeeId": "emp-001",
  "description": "Test Description"
}
```

**Expected Response:** `400 Bad Request`

#### Test Case 2.1.4: Missing Description

```http
POST /api/tasks
Content-Type: application/json

{
  "employeeId": "emp-001",
  "taskName": "Test Task"
}
```

**Expected Response:** `400 Bad Request`

#### Test Case 2.1.5: Non-existent Employee

```http
POST /api/tasks
Content-Type: application/json

{
  "employeeId": "INVALID_ID",
  "taskName": "Test Task",
  "description": "Test Description"
}
```

**Expected Response:** `400 Bad Request`

---

### 2.2 Get Tasks with Filters

**Endpoint:** `GET /api/tasks`

#### Test Case 2.2.1: Get All Tasks (No Filters)

```http
GET /api/tasks
```

**Expected Response:** `500 OK`

```json
{
  "timestamp": "2025-09-17T20:24:59.5461191",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

#### Test Case 2.2.2: Filter by Employee ID

```http
GET /api/tasks?employee_id=emp-001
```

**Expected Response:** `200 OK` (Only tasks for emp-001)

#### Test Case 2.2.3: Filter by Status

```http
GET /api/tasks?status=IN_PROGRESS
```

**Expected Response:** `200 OK` (Only IN_PROGRESS tasks)

#### Test Case 2.2.4: Filter by Due Date

```http
GET /api/tasks?due_date=2025-10-01
```

**Expected Response:** `200 OK` (Only tasks due on 2025-10-01)

#### Test Case 2.2.5: Multiple Filters

```http
GET /api/tasks?employee_id=emp-001&status=TO_DO&due_date=2025-10-01
```

**Expected Response:** `200 OK` (Tasks matching all criteria)

#### Test Case 2.2.6: Invalid Status

```http
GET /api/tasks?status=INVALID_STATUS
```

**Expected Response:** `400 Bad Request`

#### Test Case 2.2.7: Invalid Date Format

```http
GET /api/tasks?due_date=invalid-date
```

**Expected Response:** `400 Bad Request`

---

## 3. Department Management APIs

### 3.1 Get All Departments

**Endpoint:** `GET /api/departments`

#### Test Case 3.1.1: Get All Departments

```http
GET /api/departments
```

**Expected Response:** `200 OK`

```json
[
  {
    "id": "DEPT001",
    "name": "Engineering",
    "createdAt": "2025-01-01T00:00:00Z",
    "updatedAt": "2025-01-01T00:00:00Z"
  },
  {
    "id": "DEPT002",
    "name": "Human Resources",
    "createdAt": "2025-01-01T00:00:00Z",
    "updatedAt": "2025-01-01T00:00:00Z"
  },
  {
    "id": "DEPT003",
    "name": "Finance",
    "createdAt": "2025-01-01T00:00:00Z",
    "updatedAt": "2025-01-01T00:00:00Z"
  }
]
```

---

### 3.2 Create New Department

**Endpoint:** `POST /api/departments`

#### Test Case 3.2.1: Valid Department Creation

```http
POST /api/departments
Content-Type: application/json

{
  "name": "Marketing Department"
}
```

**Expected Response:** `201 Created`

```json
{
  "id": "609ba48f-fc1f-450c-9f50-6e7777b25fc6",
  "name": "Marketing Department"
}
```

#### Test Case 3.2.2: Missing Department Name

```http
POST /api/departments
Content-Type: application/json

{}
```

**Expected Response:** `400 Bad Request`

#### Test Case 3.2.3: Empty Department Name

```http
POST /api/departments
Content-Type: application/json

{
  "name": ""
}
```

**Expected Response:** `400 Bad Request`

#### Test Case 3.2.4: Duplicate Department Name

```http
POST /api/departments
Content-Type: application/json

{
  "name": "Marketing"
}
```

**Expected Response:** `400 Bad Request`

---

### 3.3 Get Department Average Salaries

**Endpoint:** `GET /api/departments/average-salaries`

#### Test Case 3.3.1: Get Average Salaries

```http
GET /api/departments/average-salaries
```

**Expected Response:** `200 OK`

```json
[
  {
    "departmentId": "DEPT001",
    "departmentName": "Engineering",
    "averageSalary": 78000.0,
    "employeeCount": 5
  },
  {
    "departmentId": "DEPT002",
    "departmentName": "Human Resources",
    "averageSalary": 65000.0,
    "employeeCount": 3
  },
  {
    "departmentId": "DEPT003",
    "departmentName": "Finance",
    "averageSalary": 72000.0,
    "employeeCount": 4
  }
]
```

---

### 3.4 Get Department Statistics

**Endpoint:** `GET /api/departments/statistics/{id}`

#### Test Case 3.4.1: Valid Department Statistics

```http
GET /api/departments/statistics/dept-056
```

**Expected Response:** `200 OK`

```json
{
  "departmentId": "DEPT001",
  "departmentName": "Engineering",
  "totalEmployees": 5,
  "averageSalary": 78000.0,
  "taskStatistics": {
    "totalTasks": 15,
    "todoTasks": 3,
    "inProgressTasks": 7,
    "completedTasks": 5
  },
  "newEmployeesLast30Days": [
    {
      "id": "EMP003",
      "fullname": "Alice Johnson",
      "email": "alice.johnson@company.com",
      "position": "Junior Developer",
      "joinDate": "2025-08-25"
    }
  ]
}
```

#### Test Case 3.4.2: Non-existent Department

```http
GET /api/departments/statistics/INVALID_DEPT
```

**Expected Response:** `404 Not Found`

---

## 4. Lunch Log Management APIs

### 4.1 Bulk Create Lunch Logs

**Endpoint:** `POST /api/lunch-logs/bulk`

#### Test Case 4.1.1: Valid Bulk Creation

```http
POST /api/lunch-logs/bulk
Content-Type: application/json

{
  "lunchLogs": [
    {
      "employeeId": "emp-001",
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH",
      "restaurant": "Pizza Palace",
      "notes": "Team lunch meeting"
    },
    {
      "employeeId": "emp-002",
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH",
      "restaurant": "Burger King",
      "notes": "Quick lunch"
    },
    {
      "employeeId": "emp-001",
      "lunchDate": "2025-09-17",
      "mealType": "DINNER",
      "restaurant": "Sushi House",
      "notes": "Working late"
    }
  ]
}
```

**Expected Response:** `201 Created`

```json
{
  "totalCreated": 3,
  "lunchLogs": [
    {
      "id": 105,
      "employeeId": "emp-001",
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH",
      "restaurant": "Pizza Palace",
      "notes": "Team lunch meeting"
    },
    {
      "id": 106,
      "employeeId": "emp-002",
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH",
      "restaurant": "Burger King",
      "notes": "Quick lunch"
    },
    {
      "id": 107,
      "employeeId": "emp-001",
      "lunchDate": "2025-09-17",
      "mealType": "DINNER",
      "restaurant": "Sushi House",
      "notes": "Working late"
    }
  ],
  "message": "Successfully created 3 lunch log entries"
}
```

#### Test Case 4.1.2: Mixed Valid and Invalid Entries

```http
POST /api/lunch-logs/bulk
Content-Type: application/json

{
  "lunchLogs": [
    {
      "employeeId": "emp-001",
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH",
      "restaurant": "Pizza Palace"
    },
    {
      "employeeId": "INVALID_ID",
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH",
      "restaurant": "Burger King"
    },
    {
      "employeeId": "EMP002",
      "lunchDate": "2025-09-17",
      "mealType": "INVALID_MEAL",
      "restaurant": "Sushi House"
    }
  ]
}
```

**Expected Response:** `400 Bad Request`

#### Test Case 4.1.3: Empty Lunch Logs List

```http
POST /api/lunch-logs/bulk
Content-Type: application/json

{
  "lunchLogs": []
}
```

**Expected Response:** `400 Bad Request`

```json
{
  "timestamp": "2025-09-17T20:39:09.8360395",
  "status": 400,
  "error": "Validation Failed",
  "message": "Request validation failed",
  "fieldErrors": {
    "lunchLogs": "Lunch logs list cannot be empty"
  }
}
```

#### Test Case 4.1.4: Too Many Entries (Over 100)

```http
POST /api/lunch-logs/bulk
Content-Type: application/json

{
  "lunchLogs": [
    // ... 101 entries
  ]
}
```

**Expected Response:** `400 Bad Request`

```json
{
  "timestamp": "2025-09-17T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Cannot process more than 100 lunch logs at once"
}
```

#### Test Case 4.1.5: Missing Required Fields

```http
POST /api/lunch-logs/bulk
Content-Type: application/json

{
  "lunchLogs": [
    {
      "lunchDate": "2025-09-17",
      "mealType": "LUNCH"
    }
  ]
}
```

**Expected Response:** `400 Bad Request`

---

## 5. Error Response Format

All error responses follow this general format:

```json
{
  "timestamp": "2025-09-17T10:30:00",
  "status": 400,
  "error": "Error Type",
  "message": "Detailed error message",
  "path": "/api/endpoint"
}
```

### Common HTTP Status Codes:

- `200 OK`: Successful GET/PUT requests
- `201 Created`: Successful POST requests
- `400 Bad Request`: Validation errors, malformed requests
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server-side errors

---

## 6. Test Data Setup

### Prerequisites

Before running any of the test cases above, ensure you have the test data properly set up in your database.

### Database Setup with Docker Compose

1. **Database Container**: The project includes a pre-configured Docker Compose setup located at `container/docker-compose.yml`

2. **Test Data**: A complete database dump file `dump-company-202509162226.sql` is provided in the `container/` directory with sample data including:

   - Sample employees (emp-001, emp-002, etc.)
   - Department records (dept-001, dept-002, dept-005, etc.)
   - Task assignments
   - Lunch log entries

3. **Quick Setup**:

   ```bash
   cd container
   docker-compose up -d
   ```

   The Docker Compose configuration automatically:

   - Sets up the MySQL database container
   - Loads the dump file with sample data
   - Configures the database connection for the Spring Boot application

### Sample Data Overview

After running the Docker setup, you'll have access to:

- **Employees**:

  - `emp-001` (John Doe - Engineering)
  - `emp-002` (Jane Smith - HR/Marketing)
  - Additional sample employees

- **Departments**:

  - `dept-001` (Engineering)
  - `dept-002` (Marketing/HR)
  - `dept-005` (Engineering)
  - Additional departments

- **Tasks**: Pre-assigned tasks with various statuses (TO_DO, IN_PROGRESS, COMPLETED)

- **Lunch Logs**: Sample meal tracking data

### Verification

Once your database is running, you can verify the setup by running:

```bash
# Start the Spring Boot application
.\mvnw.cmd spring-boot:run

# Test a simple endpoint
curl -X GET "http://localhost:8080/api/employees/emp-001"
```

### Notes

- The dump file contains realistic test data that matches all the test cases in this document
- All employee IDs, department IDs, and other references in the test cases correspond to actual data in the dump
- The Docker Compose setup ensures consistent database state across different environments

## 7. Testing Tips

1. **Swagger UI**: The project includes Swagger for API documentation and testing. Once the application is running, access it at:

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

   Swagger provides an interactive interface where you can:

   - View all available endpoints with detailed documentation
   - Test APIs directly from the browser
   - See request/response schemas
   - Execute API calls with sample data

2. **Use Postman Collections**: Import these test cases into Postman for easier testing
3. **Environment Variables**: Set up environment variables for base URL and common IDs
4. **Sequential Testing**: Run tests in logical order (create departments first, then employees, then tasks)
5. **Data Cleanup**: Clean up test data between test runs if needed
6. **Validate Responses**: Always check both status codes and response body structure
7. **Edge Cases**: Pay special attention to validation error test cases
8. **Performance**: Test with larger datasets for bulk operations
9. **API Documentation**: Use Swagger UI as your primary reference for the most up-to-date API specifications
