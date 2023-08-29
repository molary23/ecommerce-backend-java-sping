package com.hassanadeola.eCommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    public String id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Double price;
}
