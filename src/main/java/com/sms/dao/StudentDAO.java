package com.sms.dao;

import com.sms.exception.DataAccessException;
import com.sms.model.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAO {

    private final DataSource dataSource;

    public StudentDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static final String INSERT_STUDENT = """
            INSERT INTO students (first_name, last_name, email, phone, date_of_birth, course, gpa, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_BY_ID = """
            SELECT student_id, first_name, last_name, email, phone, date_of_birth,
                   enrollment_date, course, gpa, status, created_at, updated_at
            FROM students WHERE student_id = ?
            """;

    private static final String SELECT_ALL = """
            SELECT student_id, first_name, last_name, email, phone, date_of_birth,
                   enrollment_date, course, gpa, status, created_at, updated_at
            FROM students
            ORDER BY last_name, first_name
            """;

    private static final String SEARCH_BY_COURSE = """
            SELECT student_id, first_name, last_name, email, phone, date_of_birth,
                   enrollment_date, course, gpa, status, created_at, updated_at
            FROM students
            WHERE course LIKE ?
            ORDER BY last_name, first_name
            """;

    private static final String UPDATE_STUDENT = """
            UPDATE students
            SET first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ?,
                course = ?, gpa = ?, status = ?
            WHERE student_id = ?
            """;

    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_id = ?";

    private static final String LOG_HISTORY = """
            INSERT INTO enrollment_history (student_id, action, notes)
            VALUES (?, ?, ?)
            """;

    public Student create(Student student) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {

            bindStudent(ps, student);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DataAccessException("Insert failed; no rows affected.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setStudentId(keys.getInt(1));
                }
            }

            logHistory(conn, student.getStudentId(), "ENROLLED", "Student created via application");
            return student;
        } catch (SQLException ex) {
            throw mapSqlException(ex, "create student");
        }
    }

    public Optional<Student> findById(int studentId) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw mapSqlException(ex, "find student by id");
        }
    }

    public List<Student> findAll() {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            return mapList(rs);
        } catch (SQLException ex) {
            throw mapSqlException(ex, "list students");
        }
    }

    public List<Student> findByCourse(String courseKeyword) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_BY_COURSE)) {

            ps.setString(1, "%" + courseKeyword.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                return mapList(rs);
            }
        } catch (SQLException ex) {
            throw mapSqlException(ex, "search students by course");
        }
    }

    public boolean update(Student student) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_STUDENT)) {

            bindStudent(ps, student);
            ps.setInt(9, student.getStudentId());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                logHistory(conn, student.getStudentId(), "UPDATED", "Student record updated");
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw mapSqlException(ex, "update student");
        }
    }

    public boolean delete(int studentId) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_STUDENT)) {

            ps.setInt(1, studentId);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                logHistory(conn, studentId, "WITHDRAWN", "Student removed from system");
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw mapSqlException(ex, "delete student");
        }
    }

    private void bindStudent(PreparedStatement ps, Student student) throws SQLException {
        ps.setString(1, student.getFirstName().trim());
        ps.setString(2, student.getLastName().trim());
        ps.setString(3, student.getEmail().trim().toLowerCase());
        if (student.getPhone() == null || student.getPhone().isBlank()) {
            ps.setNull(4, Types.VARCHAR);
        } else {
            ps.setString(4, student.getPhone().trim());
        }
        ps.setDate(5, Date.valueOf(student.getDateOfBirth()));
        ps.setString(6, student.getCourse().trim());
        ps.setBigDecimal(7, student.getGpa());
        ps.setString(8, student.getStatus().name());
    }

    private void logHistory(Connection conn, int studentId, String action, String notes) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(LOG_HISTORY)) {
            ps.setInt(1, studentId);
            ps.setString(2, action);
            ps.setString(3, notes);
            ps.executeUpdate();
        }
    }

    private List<Student> mapList(ResultSet rs) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            students.add(mapRow(rs));
        }
        return students;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        Date enrollment = rs.getDate("enrollment_date");
        if (enrollment != null) {
            student.setEnrollmentDate(enrollment.toLocalDate());
        }
        student.setCourse(rs.getString("course"));
        student.setGpa(rs.getBigDecimal("gpa"));
        student.setStatus(Student.StudentStatus.valueOf(rs.getString("status")));
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        if (created != null) {
            student.setCreatedAt(created.toLocalDateTime());
        }
        if (updated != null) {
            student.setUpdatedAt(updated.toLocalDateTime());
        }
        return student;
    }

    private DataAccessException mapSqlException(SQLException ex, String operation) {
        String message = switch (ex.getErrorCode()) {
            case 1062 -> "Email already exists for another student.";
            case 1452 -> "Invalid reference: related record not found.";
            default -> "Database error during " + operation + ".";
        };
        return new DataAccessException(message, ex);
    }
}
