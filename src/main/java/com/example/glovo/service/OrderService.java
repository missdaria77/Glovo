package com.example.glovo.service;

import com.example.glovo.model.Order;
import com.example.glovo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();

    public OrderService() {
        Product apple = Product.builder().id(1).name("apple").cost(1).build();
        List<Product> products = List.of(apple);
        orders.add(Order.builder().cost(2).date("12/02/2022").id(1).products(products).build());
    }

    public Order get(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }

        }
        System.out.println("was not found");
        return null;
    }

    public List<Order> getAll() {
        return orders;
    }

    public void add(Order order) {
        orders.add(order);
    }

}
