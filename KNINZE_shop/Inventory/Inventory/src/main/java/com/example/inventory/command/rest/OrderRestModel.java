package com.example.inventory.command.rest;


import com.example.core.commands.Product_Core;
import lombok.Data;

import java.util.List;

@Data
public class OrderRestModel {
    private String orderId;
    private String customerId;
    private List<Product_Core> orders;
    private String orderDate;
    private double totalPrice;
}
