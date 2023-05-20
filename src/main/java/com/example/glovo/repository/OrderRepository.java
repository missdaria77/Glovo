package com.example.glovo.repository;

import com.example.glovo.entity.OrderEntity;
import com.example.glovo.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository <OrderEntity, Integer> {

    List<OrderEntity> findAll();
}
