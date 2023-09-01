package com.hassanadeola.eCommerce.services;

import com.hassanadeola.eCommerce.models.Order;
import com.hassanadeola.eCommerce.models.Payment;
import com.hassanadeola.eCommerce.repository.OrderRepository;
import com.hassanadeola.eCommerce.repository.PaymentRepository;
import com.hassanadeola.eCommerce.repository.ProductRepository;
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
