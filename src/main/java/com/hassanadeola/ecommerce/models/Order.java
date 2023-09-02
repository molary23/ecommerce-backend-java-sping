package com.hassanadeola.ecommerce.models;

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
    private List<CartItem> cartItems;
    private String userId;
    private boolean isBought = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(List<CartItem> cartItems, String userId) {
        this.cartItems = cartItems;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Order(List<CartItem> cartItems, String userId, LocalDateTime updatedAt) {
        this.cartItems = cartItems;
        this.userId = userId;
        this.updatedAt = updatedAt;
    }
}
