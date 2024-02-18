package com.example.core.commands;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.ArrayList;

@Data
@Builder
public class ReserveProductCommand {
    @TargetAggregateIdentifier
    private final String inventoryId;
    private final String orderId;
    private final String userName;
    private final ArrayList<Product_Core> orders;
    private final double totalPrice;


//    private final String orderDate;
//    private final double totalPrice;


}
