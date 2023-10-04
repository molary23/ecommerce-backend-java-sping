package com.hassanadeola.ecommerce.services;

import com.hassanadeola.ecommerce.models.*;
import com.hassanadeola.ecommerce.repository.OrderRepository;
import com.hassanadeola.ecommerce.repository.ProductRepository;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    public static final String COLLECTION_NAME = "order";
    private static final String PRODUCT_ADDED = "Product added to cart successfully";
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    HttpServletResponse httpServletResponse;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Order findOneByUserIdOrderByCreatedAtDesc(String userId) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("userId").is(userId)).with(Sort.by(Sort.Direction.DESC, "createdAt")),
                Order.class,
                COLLECTION_NAME);
    }

    public String addOrder(String productId, String userId) {
        Order order;

        order = findOneByUserIdOrderByCreatedAtDesc(userId);
        List<CartItem> cartItems;
        String response = "";

        if (order == null || order.isBought()) {
            cartItems = Collections.singletonList(new CartItem(productId, 1));
            order = new Order(cartItems, userId);
            response = PRODUCT_ADDED;
        } else {
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
                int id = cartItems.indexOf(cartItem) + 1;
                if (productId != null) {
                    Optional<Product> product = productRepository.findById(productId);
                    product.ifPresent(value -> productsInCart.add(new ProductInCart(id, value, quantity)));
                }

            }
        }
        return productsInCart;
    }

    public Object getUserCurrentOrderProducts(String userId) {
        Order order = findOneByUserIdOrderByCreatedAtDesc(userId);
        List<ProductInCart> productsInCart = new ArrayList<>();
        if (order != null && !order.isBought()) {
            List<CartItem> cartItems = order.getCartItems();
            for (CartItem cartItem : cartItems) {
                String productId = cartItem.getProductId();
                int id = cartItems.indexOf(cartItem) + 1;
                int quantity = cartItem.getQuantity();
                if (productId != null) {
                    Optional<Product> product = productRepository.findById(productId);
                    product.ifPresent(value -> productsInCart.add(new ProductInCart(id, value, quantity)));
                }

            }
        }
        return productsInCart;
    }

    public Double getTotal(String userId) {
        Order order = findOneByUserIdOrderByCreatedAtDesc(userId);
        double total = 0.0;
        if (!order.isBought()) {
            List<CartItem> cartItems = order.getCartItems();
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

    public void reduceProductQty(String productId, String userId) {
        Order order = findOneByUserIdOrderByCreatedAtDesc(userId);
        List<CartItem> cartItems = null;
        boolean isFound;
        if (order != null) {
            cartItems = order.getCartItems();
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
                order.setCartItems(cartItems);
                orderRepository.save(order);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void removeAll(String userId) {
        Order order = findOneByUserIdOrderByCreatedAtDesc(userId);
        if (!order.isBought()) {
            String orderId = order.getId();
            orderRepository.deleteById(orderId);
        }
    }

    public int getProductQtyFromOrder(String orderId, String productId) {
        Optional<Order> order = orderRepository.findById(orderId);
        List<CartItem> cartItems = null;
        int qty = 0;
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
                        qty = cartItem.getQuantity();
                    }
                }
            }

        }
        return qty;
    }


    public void removeAProductFromOrder(String productId, String userId) {
        Order order = findOneByUserIdOrderByCreatedAtDesc(userId);
        List<CartItem> cartItems = null;

        if (order != null) {
            cartItems = order.getCartItems();
        }
        if (cartItems != null) {
            cartItems = cartItems.stream().filter(cartItem -> !cartItem.getProductId().equalsIgnoreCase(productId)).toList();
            order.setCartItems(cartItems);
            orderRepository.save(order);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
