package com.hassanadeola.eCommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

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

}
