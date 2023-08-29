package com.hassanadeola.eCommerce.repository;

import com.hassanadeola.eCommerce.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    
}
