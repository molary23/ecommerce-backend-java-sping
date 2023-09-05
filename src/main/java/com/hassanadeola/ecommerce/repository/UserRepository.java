package com.hassanadeola.ecommerce.repository;


import com.hassanadeola.ecommerce.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);

    Users findByEmail(String email);
}

