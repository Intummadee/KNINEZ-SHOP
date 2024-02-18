package com.example.onlineservice.core.events;


import com.example.onlineservice.command.rest.Product;
import lombok.Data;

import java.util.ArrayList;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private String userName;
    private ArrayList<Product> orders;
    private String orderDate;
    private double totalPrice;

}
