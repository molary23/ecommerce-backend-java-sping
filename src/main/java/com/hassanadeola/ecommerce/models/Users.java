package com.hassanadeola.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Document("customers")
public class Users {
    @Id
    @Generated
    public String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Address address;
    @JsonIgnore
    private Card card;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Users(String username, String email, String password, LocalDateTime updatedAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
    }


}
