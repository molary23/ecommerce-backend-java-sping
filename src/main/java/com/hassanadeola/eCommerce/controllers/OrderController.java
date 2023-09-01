package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Order;
import com.hassanadeola.eCommerce.repository.OrderRepository;
import com.hassanadeola.eCommerce.repository.ProductRepository;
import com.hassanadeola.eCommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    public OrderService orderService;

    private static final String EMPTY_ORDER_ID_MESSAGE = "Order Id cannot be empty",
            EMPTY_PRODUCT_ID_MESSAGE = "Product Id cannot be empty";


    @PostMapping("/orders/add")
    public List<String> addOrder(String productId, String userId) {
        List<String> response = new ArrayList<>();
        if (userId == null) {
            response.add("User Id cannot be empty");
        } else if (productId == null) {
            response.add(EMPTY_PRODUCT_ID_MESSAGE);
        } else {
            response = orderService.addOrder(productId, userId);
        }
        return response;

    }

    @DeleteMapping("/orders/remove")
    public String removeProductFromOrder(String productId, String orderId) {
        String response = "";
        if (orderId == null) {
            response = EMPTY_ORDER_ID_MESSAGE;
        } else if (productId == null) {
            response = EMPTY_PRODUCT_ID_MESSAGE;
        } else {
            orderService.removeProduct(productId, orderId);
            response = "Product removed from Cart successfully";
        }
        return response;
    }

    @GetMapping("/orders/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/products")
    public Object getProductsFromOrder(String orderId) {
        if (orderId == null) {
            return EMPTY_ORDER_ID_MESSAGE;
        } else {
            return orderService.getProductsFromOrder(orderId);
        }
    }

    @GetMapping("/orders/total")
    public Double getTotal(String orderId) {
        return orderService.getTotal(orderId);
    }

    @DeleteMapping("orders/delete")
    public String deleteAllOrder(String orderId) {
        String response = "";
        if (orderId == null) {
            response = EMPTY_ORDER_ID_MESSAGE;
        } else {
            orderService.removeAll(orderId);
            response = "All cart items removed from Cart successfully";
        }
        return response;
    }
}
