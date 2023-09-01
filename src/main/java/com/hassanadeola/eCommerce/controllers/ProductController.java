package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Product;
import com.hassanadeola.eCommerce.repository.ProductRepository;
import com.hassanadeola.eCommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
/*
    @GetMapping("/products/search")
    public List<Product> searchProducts(String name, String description, int skip, int limit) {
        return productRepository.findProductsByName(name, description, skip, limit);

    }*/


}
