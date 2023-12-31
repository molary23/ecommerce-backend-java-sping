package com.hassanadeola.ecommerce.repository;

import com.hassanadeola.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByName(String name);

    Page<Product> findAll(Pageable pageable);
/*
    @Aggregation(pipeline = {
            "{ '$search': { 'name' : ?0 } }",
            "{ '$search' : { 'description' : ?1 } }",
            "{ '$skip' : ?2 }",
            "{ '$limit' : ?3 }"
    })
    List<Product> findProductsByName(String name, String description, int skip, int limit);*/

}
