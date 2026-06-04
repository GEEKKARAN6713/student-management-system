# 🎓 Student Management System

> A modern full-stack Student Management System built with **Java**, **Spring Boot**, **JDBC**, **MySQL**, and **Thymeleaf**.

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-8-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-purple?style=for-the-badge)

---

## 🚀 Overview

This project is a complete Student Management System that allows administrators to manage student records through a modern web interface.

The application demonstrates real-world backend development concepts including:

* CRUD Operations
* JDBC Database Integration
* Layered Architecture (DAO → Service → Controller)
* Input Validation
* Exception Handling
* Spring Boot MVC
* MySQL Database Design
* Deployment-Ready Configuration

---

# 📸 Screenshots

## 🏠 Dashboard

![Dashboard](screenshots/Dashboard.png)

---

## 👨‍🎓 Student List

![Student List](screenshots/StudentsList.png)

---

## ➕ Add Student

![Add Student](screenshots/AddStudent.png)

---

## ✏️ Edit Student

![Edit Student](screenshots/EditStudent.png)

---

# ✨ Features

### Student Management

* Register new students
* View student details
* Update student records
* Delete student records
* Search students by course

### Database Features

* Relational MySQL schema
* Foreign key constraints
* Indexed queries
* Audit history tracking

### Security & Validation

* Input validation
* Email validation
* GPA validation
* Structured exception handling

### Deployment Ready

* Docker support
* Railway deployment support
* Render deployment support
* Environment-based configuration

---

# 🛠️ Tech Stack

| Layer       | Technology              |
| ----------- | ----------------------- |
| Backend     | Java 17+, Spring Boot 3 |
| Database    | MySQL 8                 |
| Data Access | JDBC                    |
| Frontend    | Thymeleaf, HTML, CSS    |
| Build Tool  | Maven                   |
| Deployment  | Docker, Railway, Render |

---

# 📂 Project Structure

```text
student-management-system
│
├── sql/
│   ├── schema.sql
│   └── seed.sql
│
├── src/main/java/com/sms/
│   ├── dao/
│   ├── service/
│   ├── web/
│   ├── model/
│   ├── util/
│   └── SmsApplication.java
│
├── src/main/resources/
│   ├── templates/
│   ├── static/css/
│   └── application.properties.example
│
├── screenshots/
│   ├── dashboard.png
│   ├── students.png
│   ├── add-student.png
│   └── edit-student.png
│
├── Dockerfile
├── DEPLOYMENT.md
└── README.md
```

---

# ⚙️ Local Setup

## Prerequisites

* Java 17+
* Maven 3.9+
* MySQL 8+

---

## Database Setup

Create the database:

```sql
CREATE DATABASE student_management;
```

Run schema:

```bash
mysql -u root -p < sql/schema.sql
```

(Optional)

```bash
mysql -u root -p < sql/seed.sql
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

## Run Application

### IntelliJ IDEA

Run:

```text
com.sms.SmsApplication
```

### Maven

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

---

# 🌐 Deployment

Deployment instructions are available in:

```text
DEPLOYMENT.md
```

Supports:

* Railway
* Render
* Docker
* Custom Domains

---

# 📋 Web Routes

| Method | Endpoint              | Description       |
| ------ | --------------------- | ----------------- |
| GET    | /students             | List students     |
| GET    | /students/new         | Registration form |
| POST   | /students             | Create student    |
| GET    | /students/{id}        | View student      |
| GET    | /students/{id}/edit   | Edit student      |
| POST   | /students/{id}        | Update student    |
| POST   | /students/{id}/delete | Delete student    |

---

# 🎯 Learning Outcomes

This project demonstrates:

* Java Backend Development
* Spring Boot MVC
* JDBC Programming
* MySQL Database Design
* CRUD Application Development
* Web Application Deployment
* Git & GitHub Workflow

---

# 👨‍💻 Author

**Karan Kamble**

Final Year B.Tech (INFORMATION TECHNOLOGY)

GitHub: https://github.com/GEEKKARAN6713

---

⭐ If you found this project useful, consider starring the repository.
