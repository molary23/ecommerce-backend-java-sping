package com.hassanadeola.ecommerce.controllers;

import com.hassanadeola.ecommerce.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public class PaymentController {
    @Autowired
    PaymentService paymentService;


    @PostMapping("/payment")
    public String makePayment(String orderId, double total) {
        return paymentService.savePayment(orderId, total);
    }
}
