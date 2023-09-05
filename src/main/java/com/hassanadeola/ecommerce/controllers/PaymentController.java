package com.hassanadeola.ecommerce.controllers;

import com.hassanadeola.ecommerce.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;


    @PostMapping("/pay")
    public boolean makePayment(String userId, String orderId, double amount, String lastFour) {
        return paymentService.savePayment(orderId, amount, userId, lastFour);
    }

    @GetMapping("payment")
    public void getPayment() {

    }
}
