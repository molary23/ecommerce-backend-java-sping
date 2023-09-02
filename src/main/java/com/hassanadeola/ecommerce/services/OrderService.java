package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.CartItem;
import com.hassanadeola.ecommerce.models.Order;
import com.hassanadeola.ecommerce.models.Product;
import com.hassanadeola.ecommerce.models.ProductInCart;
import com.hassanadeola.ecommerce.repository.OrderRepository;
import com.hassanadeola.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private static final String PRODUCT_ADDED = "Product added to cart successfully";

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    HttpServletResponse httpServletResponse;

    public String addOrder(String productId, String userId) {
        Order order;

        order = orderRepository.findByUserId(userId);
        List<CartItem> cartItems;
        String response = "";
        if (order == null) {
            cartItems = Collections.singletonList(new CartItem(productId, 1));
            order = new Order(cartItems, userId);
            response = PRODUCT_ADDED;
            orderRepository.save(order);
            return response;
        }
        if (!order.isBought()) {
            cartItems = order.getCartItems();
            boolean isFound = cartItems.stream().anyMatch(cartItem -> cartItem.getProductId().equalsIgnoreCase(productId));
            if (isFound) {
                for (CartItem cartItem : cartItems) {
                    if (cartItem.getProductId().equalsIgnoreCase(productId)) {
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        response = PRODUCT_ADDED;
                    }
                }
            } else {
                cartItems.add(new CartItem(productId, 1));
                response = PRODUCT_ADDED;
            }
            order.setCartItems(cartItems);
            order.setUpdatedAt(LocalDateTime.now());
        }
        orderRepository.save(order);
        return response;

    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public Object getProductsFromOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        List<ProductInCart> productsInCart = new ArrayList<>();
        if (order.isPresent()) {
            List<CartItem> cartItems = order.get().getCartItems();
            for (CartItem cartItem : cartItems) {
                String productId = cartItem.getProductId();
                int quantity = cartItem.getQuantity();
                if (productId != null) {
                    Optional<Product> product = productRepository.findById(productId);
                    product.ifPresent(value -> productsInCart.add(new ProductInCart(value, quantity)));
                }

            }
        }
        return productsInCart;
    }

    public Double getTotal(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        double total = 0.0;
        if (order.isPresent()) {
            List<CartItem> cartItems = order.get().getCartItems();
            for (CartItem cartItem : cartItems) {
                String productId = cartItem.getProductId();
                int quantity = cartItem.getQuantity();
                Optional<Product> product = productRepository.findById(productId);
                if (product.isPresent()) {
                    total += (product.get().getPrice() * quantity);
                }
            }
        }
        return total;
    }

    public void removeProduct(String productId, String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        List<CartItem> cartItems = null;
        boolean isFound;
        if (order.isPresent()) {
            cartItems = order.get().getCartItems();
        }
        if (cartItems != null) {
            isFound = cartItems.stream().anyMatch(cartItem -> cartItem.getProductId().equalsIgnoreCase(productId));
            if (isFound) {
                for (CartItem cartItem : cartItems) {
                    String id = cartItem.getProductId();
                    if (id.equalsIgnoreCase(productId)) {
                        cartItem.setQuantity(cartItem.getQuantity() - 1);
                    }
                }
                cartItems = cartItems.stream().filter(cartItem -> cartItem.getQuantity() >= 1).toList();
                order.get().setCartItems(cartItems);
                orderRepository.save(order.get());
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void removeAll(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
