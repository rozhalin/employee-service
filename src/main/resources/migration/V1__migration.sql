CREATE TABLE departments (
     id SERIAL PRIMARY KEY,
     name VARCHAR(200) NOT NULL UNIQUE,
     description VARCHAR(500)
);

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    position VARCHAR(200) NOT NULL,
    department_id INTEGER NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);

CREATE TABLE phones (
    id SERIAL PRIMARY KEY,
    number VARCHAR (15) NOT NULL UNIQUE,
    type VARCHAR (20),
    employee_id INTEGER NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

CREATE INDEX idx_employees_last_name ON employees(last_name);
CREATE INDEX idx_employees_department_id ON employees(department_id);
CREATE INDEX idx_phones_employee_id ON phones(employee_id);
CREATE INDEX dix_departments_name ON departments(name);