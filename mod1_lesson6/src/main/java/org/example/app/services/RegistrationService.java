package org.example.app.services;

import org.example.web.dto.LoginForm;
import org.example.web.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UsersRepository<LoginForm> loginFormRepo;

    @Autowired
    public RegistrationService(UsersRepository<LoginForm> loginFormRepo) {
        this.loginFormRepo = loginFormRepo;
        this.loginFormRepo.add(new RegistrationForm("root", "123", "123"));
    }

    public boolean addUser(RegistrationForm registrationForm) {
        return loginFormRepo.add(registrationForm);
    }

    public boolean authenticate(LoginForm loginFrom) {
        return this.loginFormRepo.authenticate(loginFrom);
    }
}
