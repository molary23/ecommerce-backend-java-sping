package com.hassanadeola.eCommerce.services;

import com.hassanadeola.eCommerce.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByUsername(String username);

    public User findByEmail(String email);
}

