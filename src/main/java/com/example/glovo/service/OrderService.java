package com.example.glovo.service;

import com.example.glovo.convertor.OrderConvertor;
import com.example.glovo.convertor.ProductConvertor;
import com.example.glovo.entity.OrderEntity;
import com.example.glovo.entity.ProductEntity;
import com.example.glovo.model.Order;
import com.example.glovo.model.Product;
import com.example.glovo.repository.OrderRepository;
import com.example.glovo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    public Order get(int id) {
        return orderRepository.findById(id).map(order -> {
            List<Product> products = productRepository.findByOrder(order.getId())
                    .stream().map(ProductConvertor::productEntityToProduct).toList();
            Order orderDTO = OrderConvertor.orderEntityToOrder(order);
            orderDTO.setProducts(products);
            return orderDTO;}
            ).orElseThrow();
    }

    public List<Order> getAll() {
        return orderRepository.findAll().stream().map(OrderConvertor::orderEntityToOrder).toList();
    }

    public Order save(Order order) {
        int orderId = orderRepository.save(OrderConvertor.orderToOrderEntity(order)).getId();
        order.getProducts().stream().map(ProductConvertor::productToProductEntity)
                .peek(productEntity -> productEntity.setOrder(orderId)).forEach(productRepository::save);
        order.setId(orderId);
        return order;
    }

    public void delete(int id){
        orderRepository.deleteById(id);
    }


}
