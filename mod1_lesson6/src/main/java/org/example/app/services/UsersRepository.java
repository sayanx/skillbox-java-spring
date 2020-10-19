package org.example.app.services;

import org.example.web.dto.LoginForm;
import org.example.web.dto.RegistrationForm;
import org.springframework.stereotype.Repository;

public interface UsersRepository<T> {
    boolean add(RegistrationForm registrationForm);
    boolean authenticate(LoginForm loginForm);
}
