package com.hassanadeola.eCommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    public String id;
    @NonNull
    private List<String> productId;
    @NonNull
    private String userId;
    @NonNull
    private boolean isBought = false;
}
