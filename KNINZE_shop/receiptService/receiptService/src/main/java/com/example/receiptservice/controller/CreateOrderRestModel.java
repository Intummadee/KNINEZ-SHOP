package com.example.receiptservice.controller;

import com.example.core.commands.Product_Core;
import lombok.Data;

import java.util.ArrayList;


@Data
public class CreateOrderRestModel {
    private String userName;
    private ArrayList<Product_Core> orders;
    private String orderDate;
    private double totalPrice;
    private String email;
    private double promotion;
}
