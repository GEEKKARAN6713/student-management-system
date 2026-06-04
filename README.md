# Student Management System

Full-stack **Student Management System** built with **Java**, **JDBC**, **MySQL**, and **Spring Boot**. Includes a responsive web UI, validation, exception handling, and deployment-ready Docker config.

![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)

## Features

- Relational MySQL schema with constraints, indexes, and audit history
- JDBC `PreparedStatement` CRUD via DAO layer
- Spring Boot web UI (list, search, register, edit, delete)
- Input validation and structured exceptions
- Docker + Render blueprint for deployment

## Tech stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17+, Spring Boot 3 |
| Data | JDBC, MySQL 8 |
| UI | Thymeleaf, HTML/CSS |
| Build | Maven |

## Quick start (local)

### Prerequisites

- JDK 17+
- Maven 3.9+ (or IntelliJ with Maven)
- MySQL 8 running locally

### Database

```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/seed.sql   # optional
```

### Configuration

```powershell
copy src\main\resources\application.properties.example src\main\resources\application.properties
```

Edit `application.properties` with your MySQL credentials.

### Run

**IntelliJ:** Run `com.sms.SmsApplication`

**Maven:**

```bash
mvn spring-boot:run
```

Open **http://localhost:8080**

## Project structure

```
├── sql/                    # Database schema & seed data
├── src/main/java/com/sms/
│   ├── SmsApplication.java
│   ├── dao/                # JDBC data access
│   ├── service/            # Business logic
│   ├── web/                # Controllers
│   ├── model/
│   └── util/
├── src/main/resources/
│   ├── templates/          # Thymeleaf pages
│   └── static/css/         # UI styles
├── Dockerfile
├── DEPLOYMENT.md           # GitHub + go-live guide
└── render.yaml
```

## Deploy & GitHub

See **[DEPLOYMENT.md](DEPLOYMENT.md)** for:

- Pushing to GitHub  
- Hosting on Railway / Render  
- Connecting a custom domain  
- Production environment variables  

## API routes (web)

| Method | Path | Action |
|--------|------|--------|
| GET | `/students` | List / search by course |
| GET | `/students/new` | Registration form |
| POST | `/students` | Create student |
| GET | `/students/{id}` | View details |
| GET | `/students/{id}/edit` | Edit form |
| POST | `/students/{id}` | Update |
| POST | `/students/{id}/delete` | Delete |

## License

MIT — free for learning and portfolios.
