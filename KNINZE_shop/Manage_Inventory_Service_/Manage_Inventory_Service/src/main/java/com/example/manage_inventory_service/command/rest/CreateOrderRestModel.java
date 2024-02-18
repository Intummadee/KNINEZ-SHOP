package com.example.manage_inventory_service.command.rest;



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


}
