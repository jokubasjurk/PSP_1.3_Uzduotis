package com.user.web.userweb.jpa;

import com.user.web.userweb.model.User;
import com.user.web.userweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new User("Tomas", "Tomaitis", "865345672", "vardaspavarde@yahoo.com", "test", "sl4pt4z()d1$"));
        repository.save(new User("Jonas", "Jonaitis", "+37064721548", "vardaspavarde@gmail.com", "test", "p455w()rd4$"));
        repository.save(new User("Ajus", "Ajauskas", "864721547", "ajus.nesakysiu@yahoo.com", "test", "Drak0nas123%"));
        repository.save(new User("Vaskas", "Digipasas", "865821224", "valera-nera@vu.mif.lt", "test", "kOlUmb1j4$"));
    }
}
