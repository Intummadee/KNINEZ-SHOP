package com.example.onlineservice.command.rest;


import lombok.Data;

@Data
public class Product {
    private String category;
    private String name;
    private String subcategory;
    private double cost;
    private int amount;
    private String url;
    private String nameEng;
}
