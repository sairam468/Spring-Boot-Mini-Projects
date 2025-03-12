package com.sit.exception;  

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import org.springframework.dao.DataIntegrityViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDuplicateEmailException(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("errMsg", "Email Already exists");
        return "signup";  // Make sure "register" is the correct view name
    }
}
