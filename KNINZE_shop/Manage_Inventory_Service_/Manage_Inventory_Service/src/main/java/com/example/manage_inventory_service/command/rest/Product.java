package com.example.manage_inventory_service.command.rest;

import lombok.Data;

@Data
public class Product {
    private String category;
    private String name;
    private String subcategory;
    private double cost;
    private int amount;
    private String url;
}
