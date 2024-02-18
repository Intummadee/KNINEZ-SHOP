package com.example.inventory.command;


import com.example.core.commands.Product_Core;
import com.example.core.commands.ReserveProductCommand;
import com.example.core.compensation.command.UndoAmountProductCommand;
import com.example.core.events.ProductReservedEvent;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;

import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;


@Aggregate

public class InventoryAggregate {

    @AggregateIdentifier
    private String inventoryId;
    private String orderId;

    private String userName;
    private ArrayList<Product_Core> orders;
    private String email;

    public InventoryAggregate(){}



    @CommandHandler
    public InventoryAggregate(ReserveProductCommand reserveProductCommand){
        System.out.println("เข้า ReserveProductCommand");
        ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
                .inventoryId(reserveProductCommand.getInventoryId())
                .orderId(reserveProductCommand.getOrderId())
                .userName(reserveProductCommand.getUserName())
                .orders(reserveProductCommand.getOrders())
                .totalPrice(reserveProductCommand.getTotalPrice())
                .email(reserveProductCommand.getEmail())
                .build();
        AggregateLifecycle.apply(productReservedEvent);
    }



    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent){
        this.orderId = productReservedEvent.getOrderId();
        this.userName = productReservedEvent.getUserName();
        this.orders = productReservedEvent.getOrders();
        this.inventoryId = productReservedEvent.getInventoryId();
        this.email = productReservedEvent.getEmail();
    }








}
