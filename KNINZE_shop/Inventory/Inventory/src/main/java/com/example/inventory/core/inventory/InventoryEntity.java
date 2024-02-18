package com.example.inventory.core.inventory;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Product")
public class InventoryEntity implements Serializable {

    @Id
    private String _id;
    private String category;
    private String subcategory;
    private String name;
    private double cost;
    private int amount;
    private String url;
    private String nameEng;

    public InventoryEntity(String _id , String category, String subcategory, String name, double cost, int amount, String url, String nameEng){
        this._id = _id;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.url = url;
        this.nameEng = nameEng;
    }

    public void decreaseQuantity(int quantity) {
        this.amount -= quantity;
    }

}
