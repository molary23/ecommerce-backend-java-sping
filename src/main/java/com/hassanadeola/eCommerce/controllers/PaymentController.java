package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Order;
import com.hassanadeola.eCommerce.models.Payment;
import com.hassanadeola.eCommerce.repository.OrderRepository;
import com.hassanadeola.eCommerce.repository.PaymentRepository;
import com.hassanadeola.eCommerce.repository.ProductRepository;
import com.hassanadeola.eCommerce.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

public class PaymentController {
    @Autowired
    PaymentService paymentService;


    @PostMapping("/payment")
    public String makePayment(String orderId, double total) {
        return paymentService.savePayment(orderId, total);
    }
}
