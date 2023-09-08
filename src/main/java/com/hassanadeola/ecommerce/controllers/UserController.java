package com.hassanadeola.ecommerce.controllers;

import com.hassanadeola.ecommerce.models.Address;
import com.hassanadeola.ecommerce.models.Card;
import com.hassanadeola.ecommerce.models.Users;
import com.hassanadeola.ecommerce.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Users> getUsers() {
        return userService.findUsers();
    }

    @PostMapping("/auth")
    public Object login(String username, String password) {
        Object response = "";
        if (username == null) {
            response = "Username and Email cannot be empty";
        } else if (password == null) {
            response = "Password cannot be empty";
        } else {
            response = userService.login(username, password);
        }
        return response;

    }

    @PutMapping("/address")
    public Object saveAddress(String id, Address address) {
        Object response = "";
        if (id == null) {
            response = "Users Id cannot be empty";
        } else if (address == null) {
            response = "Address cannot be empty";
        } else {
            response = userService.saveAddress(id, address);
        }
        return response;
    }

    @PutMapping("/card")
    public Object saveCard(String userId, Card card) {
        Object response = "";
        if (userId == null) {
            response = "Users Id cannot be empty";
        } else if (card == null) {
            response = "Card cannot be empty";
        } else {
            response = userService.saveCard(userId, card);
        }
        return response;

    }

    @GetMapping("/user/card")
    public Card getUserCard(String userId) {
        return userService.getUserCard(userId);
    }


}
