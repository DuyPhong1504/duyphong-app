
CREATE TABLE employees
(
    id varchar(255) not null primary key,
    username   varchar(255) not null unique,
    email      varchar(255) not null unique,
    fullname   varchar(255) not null,
    department varchar(255) null,
    position   varchar(255) null,
    salary     int          null,
    created_at timestamp    not null,
    updated_at timestamp    not null
);


CREATE TABLE departments
(
    id   VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE department_history
(
    id                  INT AUTO_INCREMENT
        PRIMARY KEY,
    employee_id         VARCHAR(255) NOT NULL,
    old_department_id   VARCHAR(255) NULL,
    new_department_id   VARCHAR(255) NOT NULL,
    change_date         TIMESTAMP    NOT NULL,
    CONSTRAINT fk_employee_id
        FOREIGN KEY (employee_id) REFERENCES employees (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_old_department
        FOREIGN KEY (old_department_id) REFERENCES departments (id)
            ON DELETE SET NULL,
    CONSTRAINT fk_new_department
        FOREIGN KEY (new_department_id) REFERENCES departments (id)
            ON DELETE RESTRICT
);


CREATE TABLE tasks
(
    id          INT AUTO_INCREMENT
        PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    task_name   VARCHAR(255) NOT NULL,
    description TEXT         NULL,
    due_date    DATE         NULL,
    status      VARCHAR(50)  NOT NULL COMMENT 'e.g., TO_DO, IN_PROGRESS, COMPLETED',
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tasks_employee
        FOREIGN KEY (employee_id) REFERENCES employees (id)
            ON DELETE CASCADE
);


CREATE TABLE lunch_logs
(
    id            INT AUTO_INCREMENT
        PRIMARY KEY,
    employee_id   VARCHAR(255) NOT NULL,
    lunch_date    DATE         NOT NULL,
    meal_type     VARCHAR(50)  NOT NULL COMMENT 'e.g., Lunch, Dinner',
    restaurant    VARCHAR(255) NULL,
    notes         TEXT         NULL,
    CONSTRAINT fk_lunch_employee
        FOREIGN KEY (employee_id) REFERENCES employees (id)
            ON DELETE CASCADE
);
