-- H2 Schema for DuyPhong App
-- Converted from MySQL dump for development environment

-- Table structure for table `departments`
CREATE TABLE IF NOT EXISTS departments (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table structure for table `employees`
CREATE TABLE IF NOT EXISTS employees (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    fullname VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    position VARCHAR(255),
    salary INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_employee_department FOREIGN KEY (department) REFERENCES departments(id)
);

-- Table structure for table `employee_department`
CREATE TABLE IF NOT EXISTS employee_department (
    employee_id VARCHAR(255) NOT NULL,
    department_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_emp_dept_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_emp_dept_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- Table structure for table `department_history`
CREATE TABLE IF NOT EXISTS department_history (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    old_department_id VARCHAR(255),
    new_department_id VARCHAR(255) NOT NULL,
    change_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_dept_hist_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    CONSTRAINT fk_dept_hist_new_dept FOREIGN KEY (new_department_id) REFERENCES departments(id),
    CONSTRAINT fk_dept_hist_old_dept FOREIGN KEY (old_department_id) REFERENCES departments(id)
);

-- Table structure for table `tasks`
CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tasks_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Table structure for table `lunch_logs`
CREATE TABLE IF NOT EXISTS lunch_logs (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    lunch_date DATE NOT NULL,
    meal_type VARCHAR(50) NOT NULL,
    restaurant VARCHAR(255),
    notes TEXT,
    CONSTRAINT fk_lunch_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);