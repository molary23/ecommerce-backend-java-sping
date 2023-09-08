package com.hassanadeola.ecommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Document("products")
public class Product {
    @Id
    public String id;
    @NonNull
    @TextIndexed(weight = 3)
    private String name;
    @NonNull
    @TextIndexed
    private String description;
    @NonNull
    private Double price;
    @NonNull
    private String image;
    @NonNull
    private Double rating;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private LocalDateTime updatedAt;


}
