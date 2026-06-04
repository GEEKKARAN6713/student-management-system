USE student_management;

INSERT INTO students (first_name, last_name, email, phone, date_of_birth, course, gpa, status)
VALUES
    ('Aisha', 'Patel', 'aisha.patel@university.edu', '555-0101', '2002-03-15', 'Computer Science', 3.75, 'ACTIVE'),
    ('Marcus', 'Lee', 'marcus.lee@university.edu', '555-0102', '2001-11-22', 'Information Systems', 3.40, 'ACTIVE'),
    ('Sofia', 'Garcia', 'sofia.garcia@university.edu', NULL, '2003-07-08', 'Data Science', 3.90, 'ACTIVE')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;
