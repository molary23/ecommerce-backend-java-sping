package com.hassanadeola.ecommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    public String id;
    @NonNull
    private String number;
    @NonNull
    private String month;
    @NonNull
    private String year;
    @NonNull
    private int cvv;
}
