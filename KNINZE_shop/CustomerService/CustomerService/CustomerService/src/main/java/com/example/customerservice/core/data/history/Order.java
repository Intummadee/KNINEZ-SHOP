package com.example.customerservice.core.data.history;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Order {
    private String name;
    private String category;
    private String subcategory;
    private double cost;
    private int amount;
//    private String orderDate;
    private String url;
//    private String totalPrice;

//    ถ้าเก็บ totalPriceไว้แต่ละorder มันจะซํ้าซ้อนอะดิ  --> private String totalPrice;
}
