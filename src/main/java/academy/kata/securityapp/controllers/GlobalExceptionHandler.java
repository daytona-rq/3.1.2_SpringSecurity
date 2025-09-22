package academy.kata.securityapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

    @RequestMapping("/error-page")
    public String errorPage() {
        return "error-page";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "/access-denied";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable(Throwable throwable) {
        return "redirect:/error-page";
    }
}
