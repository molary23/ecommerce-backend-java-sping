package com.hassanadeola.eCommerce.services;

import com.hassanadeola.eCommerce.models.Order;
import com.hassanadeola.eCommerce.models.Product;
import com.hassanadeola.eCommerce.repository.OrderRepository;
import com.hassanadeola.eCommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;


    public List<String> addOrder(String productId, String userId) {
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


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public Object getProductsFromOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        List<Object>/* List<Product>*/ products = new ArrayList<>();
        if (order.isPresent()) {
            for (String productId : order.get().getProductId()) {
                Optional<Product> product = productRepository.findById(productId);
                product.ifPresent(products::add);
            }
        }
        return products;
    }

    public Double getTotal(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        double total = 0.0;
        if (order.isPresent()) {
            for (String productId : order.get().getProductId()) {
                Optional<Product> product = productRepository.findById(productId);
                if (product.isPresent()) {
                    total += product.get().getPrice();
                }
            }
        }
        return total;
    }

    public void removeProduct(String productId, String orderId) {
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

    public void removeAll(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
