package com.hassanadeola.ecommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    public String id;

    private String orderId;

    private Double amount;

    private String userId;

    private String lastFour;

    private boolean status;

    private LocalDateTime createdAt;

    public Payment(String orderId, Double amount, String userId, String lastFour, boolean status) {
        this.orderId = orderId;
        this.amount = amount;
        this.userId = userId;
        this.lastFour = lastFour;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}
