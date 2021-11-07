package com.user.web.userweb.controller;

import com.user.web.userweb.model.User;
import com.user.web.userweb.service.UserService;
import com.user.web.userweb.validation.EmailValidator;
import com.user.web.userweb.validation.PasswordChecker;
import com.user.web.userweb.validation.PhoneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserRestController {
    @Autowired
    UserService service;

    EmailValidator emailValidator = new EmailValidator();
    PhoneValidator phoneValidator = new PhoneValidator(Map.of(
            "+370", 8,
            "+48", 9
    ));
    PasswordChecker passwordChecker = new PasswordChecker(new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*')), 7);

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        return service.findById(userId);
    }

    @PostMapping("/users")
    public User registerUser(@RequestBody User user) throws IllegalArgumentException {
        validateUser(user);
        return service.add(user);
    }

    @PostMapping("/users/{userId}")
    public User updateUserById(@PathVariable int userId, @RequestBody User userInfo) throws IllegalArgumentException {
        if (!service.existsById(userId)) {
            throw new IllegalArgumentException(String.format("User with id '%s' does not exist", userId));
        }
        validateUser(userInfo);
        userInfo.setId(userId);
        return service.update(userInfo);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        service.deleteById(userId);
    }

    private void validateUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (!emailValidator.validate(user.getEmail())) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (!phoneValidator.validate(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number is invalid");
        }
        if (!passwordChecker.validate(user.getPassword())) {
            throw new IllegalArgumentException("User password format is invalid");
        }
    }
}
