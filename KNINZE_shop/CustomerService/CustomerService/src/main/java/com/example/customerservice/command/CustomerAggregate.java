package com.example.customerservice.command;


import com.example.core.commands.Product_Core;
import com.example.core.commands.UpdateCustomerCommand;
import com.example.core.events.ProductReservedEvent;
import com.example.core.events.UpdateCustomerEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.UUID;


@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerAggregateIdentifier;
    private String historyId;
    private String userName;
    private ArrayList<Product_Core> orders;

    private String orderId;
    private String email;

    public CustomerAggregate(){}


//    รับ ReserveProductCommand มาจาก saga
    @CommandHandler
    public CustomerAggregate(UpdateCustomerCommand updateCustomerCommand){
        System.out.println("เข้ามาใน Customer Service โดยผ่าน command : updateCustomerCommand");
        UpdateCustomerEvent updateCustomerEvent = UpdateCustomerEvent.builder()
                .customerAggregateIdentifier(UUID.randomUUID().toString()) // ตอนแรกทำเป็น updateCustomerCommand.getHistoryId() แต่ว่าถ้าไม่ random ใหม่มันจะขึ้นerrorเพราะค่าAggregateIdentifier มันไม่ uniqueพอ
                .historyId(updateCustomerCommand.getHistoryId())
                .userName(updateCustomerCommand.getUserName())
                .orders(updateCustomerCommand.getOrders())
                .orderId(updateCustomerCommand.getOrderId())
                .totalPrice(updateCustomerCommand.getTotalPrice())
                .email(updateCustomerCommand.getEmail())
                .build();
        AggregateLifecycle.apply(updateCustomerEvent);
    }

    @EventSourcingHandler
    public void on(UpdateCustomerEvent updateCustomerEvent){
        this.customerAggregateIdentifier = updateCustomerEvent.getCustomerAggregateIdentifier();
        this.historyId = updateCustomerEvent.getHistoryId();
        this.userName = updateCustomerEvent.getUserName();
        this.orders = updateCustomerEvent.getOrders();
        this.orderId = updateCustomerEvent.getOrderId();
        this.email = updateCustomerEvent.getEmail();
    }



}
