package com.hassanadeola.eCommerce.services;

import com.hassanadeola.eCommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;

public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByName(String name);

    Page<Product> findAllByPage(Pageable pageable);
}
