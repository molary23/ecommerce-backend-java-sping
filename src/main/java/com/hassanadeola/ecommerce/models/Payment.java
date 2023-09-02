package com.hassanadeola.ecommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    public String id;
    @NonNull
    private String orderId;
    @NonNull
    private Double amount;
    @NonNull
    private boolean status;
    @NonNull
    private LocalDateTime createdAt = LocalDateTime.now();
    @NonNull
    private LocalDateTime updatedAt = LocalDateTime.now();

}
