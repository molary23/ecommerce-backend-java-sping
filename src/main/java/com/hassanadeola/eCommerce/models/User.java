package com.hassanadeola.eCommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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
}
