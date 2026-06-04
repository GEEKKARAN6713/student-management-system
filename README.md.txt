# Student Management System

A full-stack Student Management System built using Java, Spring Boot, JDBC, MySQL and Thymeleaf.

The application allows users to manage student records through a modern web interface with complete CRUD functionality.

---

## Features

- Add new students
- View all students
- Search students
- Update student details
- Delete student records
- MySQL database integration
- Spring Boot backend
- Thymeleaf frontend
- Responsive UI

---

## Tech Stack

### Backend
- Java 21
- Spring Boot 3
- JDBC

### Frontend
- HTML
- CSS
- Thymeleaf

### Database
- MySQL

### Build Tool
- Maven

---

## Screenshots

### Dashboard

![Dashboard](screenshots/dashboard.png)

### Student List

![Student List](screenshots/students.png)

### Add Student

![Add Student](screenshots/add-student.png)

### Edit Student

![Edit Student](screenshots/edit-student.png)

---

## Project Structure

src/
├── main/
│ ├── java/com/sms
│ ├── resources/templates
│ └── resources/static
├── screenshots
└── README.md

---

## Database Setup

Create database:

```sql
CREATE DATABASE student_management;
```

Run the provided schema:

```sql
source sql/schema.sql;
```

---

## Configuration

Create:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## Run Locally

Clone repository:

```bash
git clone https://github.com/GEEKKARAN6713/student-management-system.git
```

Go into project:

```bash
cd student-management-system
```

Run:

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

---

## Author

Karan Kamble

Final Year B.Tech (ENTC)

Java | Spring Boot | MySQL | Web Development