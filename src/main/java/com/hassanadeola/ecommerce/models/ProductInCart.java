package com.hassanadeola.ecommerce.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCart {
    private int id;
    private Product product;
    private int quantity;
}
