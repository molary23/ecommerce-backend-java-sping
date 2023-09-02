package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.Order;
import com.hassanadeola.ecommerce.models.Payment;
import com.hassanadeola.ecommerce.repository.OrderRepository;
import com.hassanadeola.ecommerce.repository.PaymentRepository;
import com.hassanadeola.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PaymentRepository paymentRepository;


    public String savePayment(String orderId, double total) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(value -> value.setBought(true));
        paymentRepository.save(new Payment(orderId, total, true));
        return "Payment Successful!";
    }
}
