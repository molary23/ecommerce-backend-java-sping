package com.hassanadeola.ecommerce.repository;

import com.hassanadeola.ecommerce.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByUserIdOrderByCreatedAtDesc(String userId);

    // Order findOneByUserIdOrderByCreatedAtDesc(String userId);
}
