package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Order;
import com.hassanadeola.eCommerce.models.Payment;
import com.hassanadeola.eCommerce.repository.OrderRepository;
import com.hassanadeola.eCommerce.repository.PaymentRepository;
import com.hassanadeola.eCommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

public class PaymentController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping("/payment")
    public String makePayment(String orderId, double total) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(value -> value.setBought(true));
        paymentRepository.save(new Payment(orderId, total, true));
        return "Payment Successful!";
    }
}
