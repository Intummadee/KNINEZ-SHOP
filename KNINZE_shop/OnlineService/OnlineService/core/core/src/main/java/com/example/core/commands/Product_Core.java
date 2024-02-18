package com.example.core.commands;


import lombok.Data;

@Data
public class Product_Core {
    private final String category;
    private final String name;
    private final String subcategory;
    private final double cost;
    private final int amount;
    private final String url;
    private final double totalPrice;
    private final String nameEng;
}
