package com.hassanadeola.ecommerce.controllers;

import com.hassanadeola.ecommerce.models.Product;
import com.hassanadeola.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/product/{name}")
    public Optional<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("/products")
    public List<Product> getProducts(int page, int limit) {
        return productService.getProducts(page, limit);

    }

    @GetMapping("/products/search")
    public List<Product> searchForProduct(String search) {
        List<Product> products = new ArrayList<>();
        if (search != null) {
            products = productService.searchProducts(search);
        }
        return products;
    }


}
