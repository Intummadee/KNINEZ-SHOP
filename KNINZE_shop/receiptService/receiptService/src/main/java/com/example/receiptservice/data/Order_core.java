package com.example.receiptservice.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order_core {
     private String category;
     private String name;
     private String subcategory;
     private String cost;
     private String amount;
     private String totalPrice;
     private String date;


}
