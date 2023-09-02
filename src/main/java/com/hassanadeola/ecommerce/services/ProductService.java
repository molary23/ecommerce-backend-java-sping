package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.Product;
import com.hassanadeola.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    public Optional<Product> getProductById(@PathVariable String id) {
        return productRepository.findById(id);
    }


    public Optional<Product> getProductByName(@PathVariable String name) {
        return productRepository.findById(name);
    }


    public List<Product> getProducts(int page, int limit) {
        PageRequest request = PageRequest.of(page, limit, Sort.by("name").descending());
        Page<Product> pagination = productRepository.findAll(request);
        List<Product> products = pagination.getContent();
        return Collections.unmodifiableList(products);
    }
}
