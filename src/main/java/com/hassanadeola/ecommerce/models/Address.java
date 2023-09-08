package com.hassanadeola.ecommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Address {
    private int number;
    @NonNull
    private String street;
    private String city;
    private String zip;
    @NonNull
    private String country;
}
