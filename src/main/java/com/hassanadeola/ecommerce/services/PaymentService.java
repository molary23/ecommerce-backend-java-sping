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

    @Autowired
    OrderService orderService;


    public boolean savePayment(double amount, String userId, String lastFour) {
        Order order = orderService.findOneByUserIdOrderByCreatedAtDesc(userId);
        boolean isSaved = false;
        if (order != null) {
            order.setBought(true);
            orderRepository.save(order);
            String orderId = order.getId();
            paymentRepository.save(new Payment(orderId, amount, userId, lastFour, true));
            isSaved = true;
        }
        return isSaved;
    }
}
