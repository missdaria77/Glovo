package com.example.glovo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class Order {
    private int id;
    private String date;
    private int cost;
    private List<Product> products;
}
