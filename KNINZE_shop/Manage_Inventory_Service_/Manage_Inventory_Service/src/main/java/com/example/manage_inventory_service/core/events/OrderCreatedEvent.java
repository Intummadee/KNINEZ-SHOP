package com.example.manage_inventory_service.core.events;


import com.example.core.commands.Product_Core;
import com.example.manage_inventory_service.command.rest.Product;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class OrderCreatedEvent {
    private String orderId;

    private String userName;
    private ArrayList<Product_Core> orders;
    private String orderDate;
    private double totalPrice;
    private String email;
}
