package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.exception.ValidationException;
import com.sms.model.Student;
import com.sms.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student registerStudent(Student student) {
        ValidationUtil.validateForCreate(student);
        return studentDAO.create(student);
    }

    public Optional<Student> getStudent(int studentId) {
        ValidationUtil.parsePositiveId(String.valueOf(studentId), "Student ID");
        return studentDAO.findById(studentId);
    }

    public List<Student> listAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> searchByCourse(String courseKeyword) {
        if (courseKeyword == null || courseKeyword.isBlank()) {
            throw new ValidationException("Course search keyword is required.");
        }
        return studentDAO.findByCourse(courseKeyword);
    }

    public Student updateStudent(Student student) {
        ValidationUtil.validateForUpdate(student);
        boolean updated = studentDAO.update(student);
        if (!updated) {
            throw new ValidationException("No student found with ID " + student.getStudentId());
        }
        return studentDAO.findById(student.getStudentId())
                .orElseThrow(() -> new ValidationException("Student missing after update."));
    }

    public void deleteStudent(int studentId) {
        ValidationUtil.parsePositiveId(String.valueOf(studentId), "Student ID");
        boolean deleted = studentDAO.delete(studentId);
        if (!deleted) {
            throw new ValidationException("No student found with ID " + studentId);
        }
    }
}
