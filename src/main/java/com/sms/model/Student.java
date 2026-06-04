package com.sms.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Student {

    private Integer studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate enrollmentDate;
    private String course;
    private BigDecimal gpa;
    private StudentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED
    }

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String phone,
                   LocalDate dateOfBirth, String course, BigDecimal gpa, StudentStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
        this.gpa = gpa;
        this.status = status != null ? status : StudentStatus.ACTIVE;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "ID=%d | %s %s | %s | %s | GPA=%.2f | %s",
                studentId, firstName, lastName, email, course,
                gpa != null ? gpa.doubleValue() : 0.0,
                status
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
