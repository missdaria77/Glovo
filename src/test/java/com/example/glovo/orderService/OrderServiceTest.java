package com.example.glovo.orderService;

import com.example.glovo.entity.OrderEntity;
import com.example.glovo.entity.ProductEntity;
import com.example.glovo.model.Order;
import com.example.glovo.model.Product;
import com.example.glovo.repository.OrderRepository;
import com.example.glovo.repository.ProductRepository;
import com.example.glovo.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    @Test
    public void getOrderTest() {
        OrderEntity testOrder = OrderEntity.builder().id(1).cost(5).date("05/05/2004").build();
        ProductEntity testProduct = ProductEntity.builder().id(1).cost(5).name("orange").order(1).build();

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(testOrder));
        Mockito.when(productRepository.findByOrder(1)).thenReturn(List.of(testProduct));

        OrderService orderService = new OrderService(orderRepository, productRepository);
        Order order = orderService.get(1);

        Assertions.assertEquals(testOrder.getId(), order.getId());
        Assertions.assertEquals(testOrder.getCost(), order.getCost());
        Assertions.assertEquals(testOrder.getDate(), order.getDate());

        List<Product> products = order.getProducts();
        Assertions.assertEquals(1, products.size());

        Product product = products.get(0);
        Assertions.assertEquals(testProduct.getId(), product.getId());
        Assertions.assertEquals(testProduct.getCost(), product.getCost());
        Assertions.assertEquals(testProduct.getName(), product.getName());
        Assertions.assertEquals(testProduct.getOrder(), order.getId());
    }

    @Test
    public void getAllOrdersTest() {
        OrderEntity testOrder1 = OrderEntity.builder().id(1).cost(5).date("05/05/2004").build();
        OrderEntity testOrder2 = OrderEntity.builder().id(2).cost(6).date("05/05/2004").build();
        OrderEntity testOrder3 = OrderEntity.builder().id(3).cost(7).date("05/05/2004").build();

        Mockito.when(orderRepository.findAll()).thenReturn(List.of(testOrder1, testOrder2, testOrder3));

        OrderService orderService = new OrderService(orderRepository, productRepository);
        List<Order> orders = orderService.getAll();

        Assertions.assertEquals(3, orders.size());

    }

    @Test
    public void saveOrderTest() {
        Product testProduct = Product.builder().id(1).cost(5).name("orange").build();
        Order testOrder = Order.builder().id(1).cost(5).date("05/05/2004").products(List.of(testProduct)).build();

        ProductEntity testProductEntity = ProductEntity.builder().id(1).cost(5).name("orange").order(1).build();
        OrderEntity testOrderEntity = OrderEntity.builder().id(1).cost(5).date("05/05/2004").build();

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(testOrderEntity);
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(testProductEntity);

        OrderService orderService = new OrderService(orderRepository, productRepository);
        Order order = orderService.save(testOrder);

        Assertions.assertEquals(testOrder.getId(), order.getId());
        Assertions.assertEquals(testOrder.getCost(), order.getCost());
        Assertions.assertEquals(testOrder.getDate(), order.getDate());
    }


}
