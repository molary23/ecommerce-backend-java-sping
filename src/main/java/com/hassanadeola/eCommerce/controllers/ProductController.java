package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Product;
import com.hassanadeola.eCommerce.services.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/product/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        return productRepository.findById(id);
    }

    @GetMapping("/product/{name}")
    public Optional<Product> getProductByName(@PathVariable String name) {
        return productRepository.findById(name);
    }

    @GetMapping("/products")
    public List<Product> getProducts(int skip, int limit) {
        int page = 0, size = limit - skip;

        Page<Product> pagination = productRepository.findAllByPage((Pageable) PageRequest.of(page, size, Sort.Direction.ASC, "name"));
        List<Product> products = pagination.getContent();
        return Collections.unmodifiableList(products);
    }

}
