package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.Address;
import com.hassanadeola.ecommerce.models.Card;
import com.hassanadeola.ecommerce.models.Users;
import com.hassanadeola.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    HttpServletResponse httpServletResponse;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public Users saveUser(String username, String email, String password) {
        return userRepository.save(new Users(username, email, passwordEncoder.encode(password)));

    }


    public List<Users> findUsers() {
        return userRepository.findAll();
    }


    public Users login(String username, String password) {
        Users result = null;
        Users usersByUsername;
        usersByUsername = userRepository.findByUsername(username);
        if (usersByUsername == null) {
            Users usersByEmail = userRepository.findByEmail(username);
            if (usersByEmail != null) {
                boolean isMatch = passwordEncoder.matches(password, usersByEmail.getPassword());
                if (isMatch) {
                    result = usersByEmail;
                }
            }
        } else {
            boolean isMatch = passwordEncoder.matches(password, usersByUsername.getPassword());
            if (isMatch) {
                result = usersByUsername;
            }
        }
        if (result == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return result;
    }


    public Users saveAddress(String id, Address address) {
        Optional<Users> user;
        Users newUsers = null;

        user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setAddress(address);
            user.get().setUpdatedAt(LocalDateTime.now());
            newUsers = userRepository.save(user.get());

        }
        if (newUsers == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return newUsers;
    }


    public boolean saveCard(String id, Card card) {
        Optional<Users> user;
        Users newUsers = null;

        user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setCard(card);
            user.get().setUpdatedAt(LocalDateTime.now());
            newUsers = userRepository.save(user.get());

        }
        if (newUsers == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        return true;
    }

    public Card getUserCard(String userId) {
        Optional<Users> user = userRepository.findById(userId);
        Card card = null;
        if (user.isPresent()) {
            card = user.get().getCard();
        }
        return card;

    }
}
