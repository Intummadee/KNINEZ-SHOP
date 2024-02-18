package com.example.onlineservice.core.data;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

@Data
@Document("Product")
public class ProductEntity implements Serializable {



//    public ProductEntity() {}
//
//    public ProductEntity(String _id, String customerId, ArrayList<Product> orders, String orderDate, double totalPrice){
//        this._id = _id;
//        this.customerId = customerId;
//        this.orders = orders;
//        this.orderDate = orderDate;
//        this.totalPrice = totalPrice;
//
//    }

    @Id
    private String _id;
    private String category;
    private String subcategory;
    private String name;
    private double cost;
    private int amount;
    private String url;
    private String nameEng;

    public ProductEntity(String _id , String category, String subcategory, String name, double cost, int amount, String url, String nameEng){
        this._id = _id;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.url = url;
        this.nameEng = nameEng;
    }


}
