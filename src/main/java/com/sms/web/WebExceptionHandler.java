package com.sms.web;

import com.sms.exception.StudentManagementException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(StudentManagementException.class)
    public String handleStudentManagement(StudentManagementException ex, RedirectAttributes redirect) {
        redirect.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/students";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
