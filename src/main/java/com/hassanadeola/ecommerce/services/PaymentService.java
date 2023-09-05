package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.Order;
import com.hassanadeola.ecommerce.models.Payment;
import com.hassanadeola.ecommerce.repository.OrderRepository;
import com.hassanadeola.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentRepository paymentRepository;


    public boolean savePayment(String orderId, double amount, String userId, String lastFour) {
        Optional<Order> order = orderRepository.findById(orderId);
        boolean isSaved = false;
        if (order.isPresent()) {
            order.get().setBought(true);
            orderRepository.save(order.get());
            paymentRepository.save(new Payment(orderId, amount, userId, lastFour, true));
            isSaved = true;
        }
        return isSaved;
    }
}
