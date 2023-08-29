package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Address;
import com.hassanadeola.eCommerce.models.Card;
import com.hassanadeola.eCommerce.models.User;
import com.hassanadeola.eCommerce.services.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUsers(String username, String email, String password) {
        userRepository.save(new User(username, email, passwordEncoder.encode(password)));
        return "User registered successfully!";
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/auth")
    public User login(String username, String email, String password, HttpServletResponse response) {
        User result = null;
        User userByUsername;
        userByUsername = userRepository.findByUsername(username);
        if (userByUsername == null) {
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail != null) {
                boolean isMatch = passwordEncoder.matches(password, userByEmail.getPassword());
                if (isMatch) {
                    result = userByEmail;
                }
            }
        } else {
            boolean isMatch = passwordEncoder.matches(password, userByUsername.getPassword());
            if (isMatch) {
                result = userByUsername;
            }
        }
        if (result == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return result;
    }

    @PutMapping("/address")
    public User saveAddress(String id, Address address, HttpServletResponse response) {
        Optional<User> user;
        User newUser = null;

        user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setAddress(address);
            newUser = userRepository.save(user.get());

        }
        if (newUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return newUser;
    }

    @PutMapping("/card")
    public User saveCard(String id, Card card, HttpServletResponse response) {
        Optional<User> user;
        User newUser = null;

        user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setCard(card);
            newUser = userRepository.save(user.get());

        }
        if (newUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return newUser;
    }

}
