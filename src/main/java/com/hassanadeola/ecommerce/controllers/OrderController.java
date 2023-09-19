package com.hassanadeola.ecommerce.controllers;

import com.hassanadeola.ecommerce.models.Order;
import com.hassanadeola.ecommerce.models.Product;
import com.hassanadeola.ecommerce.repository.OrderRepository;
import com.hassanadeola.ecommerce.repository.ProductRepository;
import com.hassanadeola.ecommerce.services.OrderService;
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
            EMPTY_PRODUCT_ID_MESSAGE = "Product Id cannot be empty",
            EMPTY_USER_ID_MESSAGE = "User Id cannot be empty";


    @PostMapping("/orders/add")
    public String addOrder(String productId, String userId) {
        String response = "";
        if (userId == null) {
            response = "Users Id cannot be empty";
        } else if (productId == null) {
            response = EMPTY_PRODUCT_ID_MESSAGE;
        } else {
            response = orderService.addOrder(productId, userId);
        }
        return response;

    }

    @DeleteMapping("/orders/remove")
    public Object removeProductFromOrder(String productId, String userId) {
        String response = "";
        if (userId == null) {
            response = EMPTY_ORDER_ID_MESSAGE;
        } else if (productId == null) {
            response = EMPTY_PRODUCT_ID_MESSAGE;
        } else {
            orderService.removeProduct(productId, userId);
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

    @GetMapping("/orders/user/products")
    public Object getUserCurrentOrderProducts(String userId) {
        if (userId == null) {
            return "User Id is required";
        } else {
            return orderService.getUserCurrentOrderProducts(userId);
        }
    }

    @GetMapping("/orders/total")
    public Double getTotal(String userId) {
        return orderService.getTotal(userId);
    }

    @DeleteMapping("orders/delete")
    public String deleteAllOrder(String userId) {
        String response = "";
        if (userId == null) {
            response = EMPTY_ORDER_ID_MESSAGE;
        } else {
            orderService.removeAll(userId);
            response = "All cart items removed from Cart successfully";
        }
        return response;
    }

    @GetMapping("/orders/product/quantity")
    public int getProductQtyFromOrder(String orderId, String productId) {
        int response = 0;
        if (orderId != null && productId != null) {
            response = orderService.getProductQtyFromOrder(orderId, productId);
        }
        return response;
    }

    @GetMapping("/products/product")
    public List<Product> searchForProduct(String search) {
        List<Product> products = new ArrayList<Product>();
        if (search != null) {
            products = orderService.searchProducts(search);
        }
        return products;
    }

    @DeleteMapping("orders/delete/product")
    public String removeAProduct(String productId, String userId) {
        String response = "";
        if (userId == null) {
            response = EMPTY_USER_ID_MESSAGE;
        } else if (productId == null) {
            response = EMPTY_PRODUCT_ID_MESSAGE;
        } else {
            orderService.removeAProductFromOrder(productId, userId);
            response = "Product removed from order";
        }
        return response;
    }
}
