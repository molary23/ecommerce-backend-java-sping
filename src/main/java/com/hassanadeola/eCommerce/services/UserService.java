package com.hassanadeola.eCommerce.services;

import com.hassanadeola.eCommerce.models.Address;
import com.hassanadeola.eCommerce.models.Card;
import com.hassanadeola.eCommerce.models.User;
import com.hassanadeola.eCommerce.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    public String saveUser(String username, String email, String password) {
        userRepository.save(new User(username, email, passwordEncoder.encode(password), LocalDateTime.now()));
        return "User registered successfully!";
    }


    public List<User> findUsers() {
        return userRepository.findAll();
    }


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


    public User saveAddress(String id, Address address, HttpServletResponse response) {
        Optional<User> user;
        User newUser = null;

        user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setAddress(address);
            user.get().setUpdatedAt(LocalDateTime.now());
            newUser = userRepository.save(user.get());

        }
        if (newUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return newUser;
    }


    public User saveCard(String id, Card card, HttpServletResponse response) {
        Optional<User> user;
        User newUser = null;

        user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setCard(card);
            user.get().setUpdatedAt(LocalDateTime.now());
            newUser = userRepository.save(user.get());

        }
        if (newUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return newUser;
    }

}
