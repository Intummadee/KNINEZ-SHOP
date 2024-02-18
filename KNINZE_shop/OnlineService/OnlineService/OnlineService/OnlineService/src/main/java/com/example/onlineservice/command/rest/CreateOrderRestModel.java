package com.example.onlineservice.command.rest;


import lombok.Data;

import java.util.ArrayList;

@Data
public class CreateOrderRestModel {

    private String userName;
    private ArrayList<Product> orders;
    private String orderDate;
    private double totalPrice;

//    @Id
//    private String _id;


}
