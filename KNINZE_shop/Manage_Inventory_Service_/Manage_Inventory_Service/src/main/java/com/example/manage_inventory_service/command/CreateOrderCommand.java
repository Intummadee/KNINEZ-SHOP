package com.example.manage_inventory_service.command;


import com.example.core.commands.Product_Core;
import com.example.manage_inventory_service.command.rest.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.ArrayList;

@Builder // เป็นของ Lombok
@Data
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    private final String userName;
    private final ArrayList<Product_Core> orders;
    private final String orderDate;
    private final double totalPrice;
    private final String email;


}
