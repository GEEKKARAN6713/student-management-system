package com.sms.web;

import com.sms.model.Student;
import com.sms.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String course, Model model) {
        List<Student> students = (course != null && !course.isBlank())
                ? studentService.searchByCourse(course)
                : studentService.listAllStudents();

        model.addAttribute("students", students);
        model.addAttribute("courseFilter", course != null ? course : "");
        model.addAttribute("totalCount", students.size());
        return "students/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable int id, Model model) {
        Student student = studentService.getStudent(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID " + id));
        model.addAttribute("student", student);
        return "students/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", emptyStudent());
        model.addAttribute("formTitle", "Register Student");
        model.addAttribute("formAction", "/students");
        model.addAttribute("isEdit", false);
        return "students/form";
    }

    @PostMapping
    public String create(@ModelAttribute Student student, RedirectAttributes redirect) {
        Student saved = studentService.registerStudent(student);
        redirect.addFlashAttribute("successMessage", "Student registered successfully (ID " + saved.getStudentId() + ").");
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        Student student = studentService.getStudent(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID " + id));
        model.addAttribute("student", student);
        model.addAttribute("formTitle", "Update Student");
        model.addAttribute("formAction", "/students/" + id);
        model.addAttribute("isEdit", true);
        return "students/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute Student student, RedirectAttributes redirect) {
        student.setStudentId(id);
        studentService.updateStudent(student);
        redirect.addFlashAttribute("successMessage", "Student updated successfully.");
        return "redirect:/students/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        studentService.deleteStudent(id);
        redirect.addFlashAttribute("successMessage", "Student deleted successfully.");
        return "redirect:/students";
    }

    private Student emptyStudent() {
        Student student = new Student();
        student.setStatus(Student.StudentStatus.ACTIVE);
        student.setGpa(BigDecimal.ZERO);
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));
        return student;
    }
}
