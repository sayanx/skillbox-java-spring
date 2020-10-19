package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.example.web.dto.RegistrationForm;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class LoginFormRepository implements UsersRepository<LoginForm> {
    private final HashMap<String, String> repo = new HashMap<>();
    private final Logger logger = Logger.getLogger(LoginFormRepository.class);

    @Override
    public boolean add(RegistrationForm registrationForm) {

        if (registrationForm.getPassword().equals(registrationForm.getRepeatPassword())) {
            if (repo.get(registrationForm.getUsername()) == null) {
                repo.put(registrationForm.getUsername(), registrationForm.getPassword());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean authenticate(LoginForm loginForm) {
        logger.info("authenticate with " + loginForm + "\n" + repo);
        String password = repo.get(loginForm.getUsername());
        if (password != null) {
            return password.equals(loginForm.getPassword());
        }

        return false;
    }
}
