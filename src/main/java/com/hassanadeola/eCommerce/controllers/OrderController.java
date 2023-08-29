package com.hassanadeola.eCommerce.controllers;

import com.hassanadeola.eCommerce.models.Order;
import com.hassanadeola.eCommerce.models.Product;
import com.hassanadeola.eCommerce.repository.OrderRepository;
import com.hassanadeola.eCommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/orders/add")
    public List<String> saveOrder(String productId, String userId) {
        Order fetchedOrder = orderRepository.findByUserId(userId);
        List<String> products = null;
        boolean status = false;
        if (fetchedOrder != null) {
            status = fetchedOrder.isBought();

            if (!status) {
                products = fetchedOrder.getProductId();
                products.add(productId);
                fetchedOrder.setProductId(products);
                orderRepository.save(fetchedOrder);
            }
        } else {
            products = Collections.singletonList(productId);
            orderRepository.save(new Order(products, userId));
        }
        return products;
    }

    @PutMapping("/orders/remove")
    public void removeOrder(String productId, String orderId) {
        Optional<Order> fetchedOrder = orderRepository.findById(orderId);
        List<String> products = null;
        if (fetchedOrder.isPresent()) {
            products = fetchedOrder.get().getProductId();
        }

        assert products != null;
        List<String> newProducts = products.stream().filter(product -> product.equals(productId)).toList();
        fetchedOrder.get().setProductId(newProducts);
        orderRepository.save(fetchedOrder.get());
    }

    @GetMapping("/orders/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/products")
    public List<Product> getProductsFromOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        List<Product> products = new ArrayList<>();
        if (order.isPresent()) {
            for (String productId : order.get().getProductId()) {
                if (productRepository.findById(productId).isPresent()) {
                    products.add(productRepository.findById(productId).get());
                }
            }
        }
        return products;
    }

    @GetMapping("/orders/total")
    public Double getTotal(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        double total = 0.0;
        if (order.isPresent()) {
            for (String productId : order.get().getProductId()) {
                if (productRepository.findById(productId).isPresent()) {
                    total += productRepository.findById(productId).get().getPrice();
                }
            }
        }
        return total;
    }


}
