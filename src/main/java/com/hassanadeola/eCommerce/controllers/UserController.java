package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Address;
import com.hassanadeola.eCommerce.models.Card;
import com.hassanadeola.eCommerce.models.User;
import com.hassanadeola.eCommerce.repository.UserRepository;
import com.hassanadeola.eCommerce.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUsers(String username, String email, String password) {
        String response = "";
        if (username == null) {
            response = "Username cannot be empty";
        } else if (email == null) {
            response = "Email cannot be empty";
        } else if (password == null) {
            response = "Password cannot be empty";
        } else {
            response = userService.saveUser(username, email, password);
        }
        return response;
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @PostMapping("/auth")
    public Object login(String username, String email, String password, HttpServletResponse httpServletResponse) {
        Object response = "";
        if (username == null && email == null) {
            response = "Username and Email cannot be empty";
        } else if (password == null) {
            response = "Password cannot be empty";
        } else {
            response = userService.login(username, email, password, httpServletResponse);
        }
        return response;

    }

    @PutMapping("/address")
    public Object saveAddress(String id, Address address, HttpServletResponse httpServletResponse) {
        Object response = "";
        if (id == null) {
            response = "User Id cannot be empty";
        } else if (address == null) {
            response = "Address cannot be empty";
        } else {
            response = userService.saveAddress(id, address, httpServletResponse);
        }
        return response;
    }

    @PutMapping("/card")
    public Object saveCard(String id, Card card, HttpServletResponse httpServletResponse) {
        Object response = "";
        if (id == null) {
            response = "User Id cannot be empty";
        } else if (card == null) {
            response = "Card cannot be empty";
        } else {
            response = userService.saveCard(id, card, httpServletResponse);
        }
        return response;

    }

}
