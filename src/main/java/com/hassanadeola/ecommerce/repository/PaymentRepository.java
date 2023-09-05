package com.hassanadeola.ecommerce.repository;

import com.hassanadeola.ecommerce.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    Payment findByOrderId(String orderId);
}
