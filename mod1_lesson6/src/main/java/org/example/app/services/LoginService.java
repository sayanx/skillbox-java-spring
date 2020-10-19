package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private RegistrationService registrationService;

    @Autowired
    public LoginService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        return registrationService.authenticate(loginFrom);
    }
}
