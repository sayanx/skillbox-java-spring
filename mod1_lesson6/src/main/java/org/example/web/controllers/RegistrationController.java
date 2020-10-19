package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.RegistrationService;
import org.example.web.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    private final Logger logger = Logger.getLogger(LoginController.class);
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String registration(Model model) {
        logger.info("GET /registration returns registration.html");
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration_page";
    }

    @PostMapping
    public String registration(RegistrationForm registrationForm) {
        if (registrationService.addUser(registrationForm)) {
            logger.info("registration OK redirect to book login page");
            return "redirect:/login";
        } else {
            logger.info("registration FAIL redirect back to registration");
            return "redirect:/registration";
        }
    }
}
