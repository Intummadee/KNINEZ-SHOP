package com.example.core.compensation.command;

import com.example.core.commands.Product_Core;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;


@Data
@Builder
public class UndoAmountProductCommand {
    private String userName;
    private ArrayList<Product_Core> orders;
    private String orderId;
    private String email;
    private double totalPrice;
}
