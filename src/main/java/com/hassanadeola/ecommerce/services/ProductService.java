package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.Product;
import com.hassanadeola.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;


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

    public List<Product> searchProducts(String searchTerm) {
       /* TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name")
                .onField("description")
                .build();
        mongoTemplate.indexOps(Product.class).ensureIndex(textIndex);*/

        Pattern pattern = Pattern.compile(".*" + searchTerm + ".*");
        Criteria regex = Criteria.where("name").regex(String.valueOf(pattern), "i");
        return mongoOperations.find(new Query().addCriteria(regex).limit(5), Product.class);

    }
}
