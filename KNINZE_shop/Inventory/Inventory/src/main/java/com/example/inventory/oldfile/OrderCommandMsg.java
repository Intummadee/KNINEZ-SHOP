package com.example.inventory.oldfile;

import lombok.Data;

import java.util.List;


@Data
public class OrderCommandMsg {
    private String orderId;
    private String customerId;
    private List<OrderItem> orders;
    private String orderDate;
    private double totalPrice;
}

