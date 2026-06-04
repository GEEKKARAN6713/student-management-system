package com.sms.util;

import com.sms.exception.ValidationException;
import com.sms.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9+().\\-\\s]{7,20}$");
    private static final BigDecimal MIN_GPA = BigDecimal.ZERO;
    private static final BigDecimal MAX_GPA = new BigDecimal("4.00");

    private ValidationUtil() {
    }

    public static void validateForCreate(Student student) {
        requireStudent(student);
        validateNames(student.getFirstName(), student.getLastName());
        validateEmail(student.getEmail());
        validatePhone(student.getPhone());
        validateDateOfBirth(student.getDateOfBirth());
        validateCourse(student.getCourse());
        validateGpa(student.getGpa());
        if (student.getStatus() == null) {
            student.setStatus(Student.StudentStatus.ACTIVE);
        }
    }

    public static void validateForUpdate(Student student) {
        requireStudent(student);
        if (student.getStudentId() == null || student.getStudentId() <= 0) {
            throw new ValidationException("Student ID is required for update.");
        }
        validateForCreate(student);
    }

    public static int parsePositiveId(String input, String fieldName) {
        if (input == null || input.isBlank()) {
            throw new ValidationException(fieldName + " is required.");
        }
        try {
            int id = Integer.parseInt(input.trim());
            if (id <= 0) {
                throw new ValidationException(fieldName + " must be a positive number.");
            }
            return id;
        } catch (NumberFormatException ex) {
            throw new ValidationException(fieldName + " must be a valid integer.", ex);
        }
    }

    public static LocalDate parseDate(String input, String fieldName) {
        if (input == null || input.isBlank()) {
            throw new ValidationException(fieldName + " is required (yyyy-MM-dd).");
        }
        try {
            return LocalDate.parse(input.trim());
        } catch (Exception ex) {
            throw new ValidationException(fieldName + " must use format yyyy-MM-dd.", ex);
        }
    }

    public static BigDecimal parseGpa(String input) {
        if (input == null || input.isBlank()) {
            throw new ValidationException("GPA is required.");
        }
        try {
            BigDecimal gpa = new BigDecimal(input.trim());
            validateGpa(gpa);
            return gpa;
        } catch (NumberFormatException ex) {
            throw new ValidationException("GPA must be a number between 0.00 and 4.00.", ex);
        }
    }

    public static Student.StudentStatus parseStatus(String input) {
        if (input == null || input.isBlank()) {
            return Student.StudentStatus.ACTIVE;
        }
        try {
            return Student.StudentStatus.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Status must be ACTIVE, INACTIVE, or GRADUATED.", ex);
        }
    }

    private static void requireStudent(Student student) {
        if (student == null) {
            throw new ValidationException("Student data cannot be null.");
        }
    }

    private static void validateNames(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("First name is required.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Last name is required.");
        }
        if (firstName.length() > 50 || lastName.length() > 50) {
            throw new ValidationException("Names must be at most 50 characters.");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Email format is invalid.");
        }
        if (email.length() > 100) {
            throw new ValidationException("Email must be at most 100 characters.");
        }
    }

    private static void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return;
        }
        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new ValidationException("Phone format is invalid.");
        }
    }

    private static void validateDateOfBirth(LocalDate dob) {
        if (dob == null) {
            throw new ValidationException("Date of birth is required.");
        }
        if (dob.isAfter(LocalDate.now())) {
            throw new ValidationException("Date of birth cannot be in the future.");
        }
        if (dob.isBefore(LocalDate.now().minusYears(100))) {
            throw new ValidationException("Date of birth is not realistic.");
        }
    }

    private static void validateCourse(String course) {
        if (course == null || course.isBlank()) {
            throw new ValidationException("Course is required.");
        }
        if (course.length() > 80) {
            throw new ValidationException("Course must be at most 80 characters.");
        }
    }

    private static void validateGpa(BigDecimal gpa) {
        if (gpa == null) {
            throw new ValidationException("GPA is required.");
        }
        if (gpa.compareTo(MIN_GPA) < 0 || gpa.compareTo(MAX_GPA) > 0) {
            throw new ValidationException("GPA must be between 0.00 and 4.00.");
        }
    }
}
