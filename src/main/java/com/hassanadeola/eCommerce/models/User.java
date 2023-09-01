package com.hassanadeola.eCommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Document("customers")
public class User {
    @Id
    public String id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private Address address;
    private Card card;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String username, String email, String password, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }


}
