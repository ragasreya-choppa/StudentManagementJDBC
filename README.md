# Student Management System — Java JDBC & PostgreSQL

A console-based **Student Management System** built using Core Java, JDBC, PostgreSQL, and Maven.

This project demonstrates how to perform CRUD (Create, Read, Update, Delete) operations on a relational database using JDBC, while applying Object-Oriented Programming (OOP), input validation, exception handling, and the Data Access Object (DAO) pattern.

## Features

- **Add Student:** Add new students with a unique ID, name, course, and age.
- **View All Students:** Retrieve and display student records in ascending ID order.
- **Search Student by ID:** Find a specific student using their unique ID.
- **Update Student:** Modify an existing student's name, course, and age.
- **Delete Student:** Delete a student by ID after asking for confirmation.
- **Input Validation:** Reject invalid IDs, empty names/courses, and ages outside the range of 1–120.
- **Error Handling:** Handle database exceptions, including duplicate primary keys.
- **Menu-Driven Interface:** Perform multiple operations in one session until the user chooses Exit.

Student records are stored persistently in PostgreSQL.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 20 | Application logic and OOP |
| JDBC | Database connectivity and SQL execution |
| PostgreSQL | Relational database |
| Maven | Dependency management and build |
| PostgreSQL JDBC Driver 42.7.14 | PostgreSQL JDBC connectivity |
| IntelliJ IDEA | Development environment |

## Project Structure

```text
StudentManagementJDBC/
├── pom.xml
├── .gitignore
└── src/
    └── main/
        └── java/
            └── com/
                └── sreya/
                    └── studentmanagement/
                        ├── Main.java
                        ├── model/
                        │   └── Student.java
                        ├── dao/
                        │   └── StudentDAO.java
                        └── util/
                            └── DBConnection.java
```

### Class Responsibilities

- **Main.java:** Displays the menu, accepts and validates user input, and calls DAO methods.
- **Student.java:** Represents student data using private fields, a constructor, getters, and setters.
- **StudentDAO.java:** Performs INSERT, SELECT, UPDATE, and DELETE operations using JDBC.
- **DBConnection.java:** Establishes PostgreSQL connections using JDBC and an environment variable for the database password.

## Application Architecture

```text
User
  |
  v
Main.java (Console Interface)
  |
  v
StudentDAO.java (Database Operations)
  |
  v
DBConnection.java (JDBC Connection)
  |
  v
PostgreSQL Database

Student.java represents student data
passed between the application components.
```

This separation keeps SQL operations outside the console interface and makes the application easier to maintain.

## Database Setup

### 1. Install PostgreSQL

Install PostgreSQL and make sure the database server is running on port `5432`.

### 2. Create the Database

Connect to PostgreSQL using an administrator account and execute:

```sql
CREATE DATABASE student_management;
```

Connect to the new `student_management` database before creating the table.

### 3. Create the Students Table

```sql
CREATE TABLE students (
    id INTEGER PRIMARY KEY,
    name VARCHAR(100),
    course VARCHAR(100),
    age INTEGER
);
```

The `id` column uniquely identifies each student.

### 4. Configure the Application Database User

The current `DBConnection.java` expects a PostgreSQL user named `student_app`.

Create a login role named `student_app`, assign it a secure password, and grant access to the database and table. An administrator can run:

```sql
-- Run while connected to PostgreSQL as an administrator.
-- Replace the example password with your own secure password.
CREATE ROLE student_app LOGIN PASSWORD 'replace_with_secure_password';

GRANT CONNECT ON DATABASE student_management TO student_app;
```

Then connect to the `student_management` database and run:

```sql
GRANT USAGE ON SCHEMA public TO student_app;

GRANT SELECT, INSERT, UPDATE, DELETE
ON TABLE public.students TO student_app;
```

Do not commit your actual database password to GitHub.

## How to Run the Application

### Prerequisites

- JDK 20
- Maven
- PostgreSQL running locally
- A configured `student_management` database and `students` table
- IntelliJ IDEA or another Java IDE

### Steps

**1. Clone the repository**

```bash
git clone https://github.com/ragasreya-choppa/StudentManagementJDBC.git
cd StudentManagementJDBC
```

**2. Open the project**

Open the project in IntelliJ IDEA and allow Maven to download the required dependencies.

**3. Configure the database password**

The application uses this environment variable:

```text
DB_PASSWORD
```

In IntelliJ IDEA:

1. Open **Run → Edit Configurations**.
2. Select the run configuration for `com.sreya.studentmanagement.Main`.
3. Add an environment variable named `DB_PASSWORD` containing the password for your `student_app` PostgreSQL user.
4. Save the configuration.

The JDBC connection is configured for:

```text
jdbc:postgresql://localhost:5432/student_management
```

The database username is:

```text
student_app
```

If your database host, port, name, or username differs, update `DBConnection.java` accordingly.

**4. Run the application**

Run the `Main.java` class:

```text
com.sreya.studentmanagement.Main
```

The console menu will appear.

## Sample Menu

```text
========== STUDENT MANAGEMENT SYSTEM ==========

Enter Your Choice-
1.Add Student
2.View All Students
3.Search Student by Id
4.Update Student
5.Delete Student
6.Exit
```

### Example: Add Student

```text
Enter Your Choice-
1

Add Student Selected!
Enter Student ID to add-
20
Enter Student Name to add-
Anu Kumar
Enter Student Course to add-
Java Backend
Enter Age to add-
24

Student Added SuccessFully!
```

### Example: Search Student by ID

```text
Enter Your Choice-
3

Search Student by ID Selected!
Enter Id to Search:-
20

Student Details:-
Student ID: 20
Student Name: Anu Kumar
Student Course: Java Backend
Student Age: 24
```

### Example: Delete Confirmation

```text
Enter Your Choice-
5

Delete Student Selected!
Enter Student ID to Delete:-
20

Are you sure you want to delete this student? (yes/no)
NO

Student Deletion Cancelled!
```

*These examples illustrate the expected application workflow. Actual student records depend on the local database.*

## Java and JDBC Concepts Practiced

- Object-Oriented Programming (classes, objects, encapsulation, constructors)
- Java Collections (`List` and `ArrayList`)
- JDBC (`Connection`, `PreparedStatement`, `ResultSet`)
- SQL CRUD operations
- DAO design pattern
- Exception handling using `try-catch`
- Automatic resource management using try-with-resources
- User input using `Scanner`
- Input validation
- `while` loops and `switch` statements
- Maven dependency management

## Security Practices

- Database password is read from the `DB_PASSWORD` environment variable.
- SQL parameters are bound using `PreparedStatement`.
- A dedicated PostgreSQL application user is used instead of connecting as a database administrator.
- Passwords and other secrets should never be committed to the repository.

## Future Improvements

- Refactor `Main.java` into smaller methods.
- Reuse common input-validation methods.
- Add automated tests using JUnit.
- Improve error handling and application logging.
- Add a database setup SQL script.
- Build a REST API version using Spring Boot and Spring Data JPA.

## Project Status

**Version 1 — Functional console application**

All five CRUD operations have been implemented and manually tested with PostgreSQL. Code refactoring, automated testing, and additional improvements are planned.

## Author

GitHub: [ragasreya-choppa](https://github.com/ragasreya-choppa)

---

*This project was developed as part of hands-on Java backend development learning.*
