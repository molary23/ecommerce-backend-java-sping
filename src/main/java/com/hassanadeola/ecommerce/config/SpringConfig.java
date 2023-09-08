package com.hassanadeola.ecommerce.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@DependsOn("mongoTemplate")
public class SpringConfig {

    private final String COLLECTION_NAME = "products";
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        mongoTemplate.indexOps(COLLECTION_NAME) // collection name string or .class
                .ensureIndex(
                        new Index().on("name", Sort.Direction.DESC)/*.on("description", Sort.Direction.DESC)*/
                );
    }

}
