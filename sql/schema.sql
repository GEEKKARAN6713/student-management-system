-- Student Management System — MySQL schema
-- Run: mysql -u root -p < sql/schema.sql

CREATE DATABASE IF NOT EXISTS student_management
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE student_management;

CREATE TABLE IF NOT EXISTS students (
    student_id   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    email        VARCHAR(100) NOT NULL,
    phone        VARCHAR(20),
    date_of_birth DATE        NOT NULL,
    enrollment_date DATE      NOT NULL DEFAULT (CURRENT_DATE),
    course       VARCHAR(80)  NOT NULL,
    gpa          DECIMAL(3,2) NOT NULL DEFAULT 0.00,
    status       ENUM('ACTIVE', 'INACTIVE', 'GRADUATED') NOT NULL DEFAULT 'ACTIVE',
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uq_students_email UNIQUE (email),
    CONSTRAINT chk_students_gpa CHECK (gpa >= 0.00 AND gpa <= 4.00),
    CONSTRAINT chk_students_names CHECK (
        CHAR_LENGTH(TRIM(first_name)) > 0 AND CHAR_LENGTH(TRIM(last_name)) > 0
    )
) ENGINE=InnoDB;

CREATE INDEX idx_students_last_name ON students (last_name);
CREATE INDEX idx_students_course_status ON students (course, status);

CREATE TABLE IF NOT EXISTS enrollment_history (
    history_id   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    student_id   INT UNSIGNED NOT NULL,
    action       ENUM('ENROLLED', 'UPDATED', 'WITHDRAWN', 'GRADUATED') NOT NULL,
    notes        VARCHAR(255),
    recorded_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_history_student
        FOREIGN KEY (student_id) REFERENCES students (student_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_history_student ON enrollment_history (student_id, recorded_at);
