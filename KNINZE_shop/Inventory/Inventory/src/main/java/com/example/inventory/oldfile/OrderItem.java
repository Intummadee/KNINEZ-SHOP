package com.example.inventory.oldfile;

import lombok.Data;

@Data
public class OrderItem {
    private String category;
    private String subcategory;
    private String name;
    private double cost;
    private int amount;

}
