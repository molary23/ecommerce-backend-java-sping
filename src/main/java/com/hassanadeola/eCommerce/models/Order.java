package com.hassanadeola.eCommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class Order {
    @Id
    @Generated
    public String id;
    private List<String> productId;
    private String userId;
    private boolean isBought = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(List<String> productId, String userId) {
        this.productId = productId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
