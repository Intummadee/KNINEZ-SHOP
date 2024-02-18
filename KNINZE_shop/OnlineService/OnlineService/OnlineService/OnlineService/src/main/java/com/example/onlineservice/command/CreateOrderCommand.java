package com.example.onlineservice.command;


import com.example.onlineservice.command.rest.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.ArrayList;

@Builder // เป็นของ Lombok
@Data
public class CreateOrderCommand {


    private final String orderId;
    @TargetAggregateIdentifier
    private final String userName;
    private final ArrayList<Product> orders;
    private final String orderDate;
    private final double totalPrice;





}
