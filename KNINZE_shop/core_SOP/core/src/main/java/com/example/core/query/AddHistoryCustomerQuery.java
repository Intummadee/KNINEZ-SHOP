package com.example.core.query;


import com.example.core.commands.Product_Core;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Builder
public class AddHistoryCustomerQuery {
    private String userName;
    private ArrayList<Product_Core> orders;
    private String orderId;
    private String email;
    private double totalPrice;

}
