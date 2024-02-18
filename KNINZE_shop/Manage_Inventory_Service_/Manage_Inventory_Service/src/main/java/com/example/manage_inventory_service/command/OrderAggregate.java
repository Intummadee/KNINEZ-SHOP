package com.example.manage_inventory_service.command;



import com.example.core.OrderStatus;
import com.example.core.commands.ApproveOrderCommand;
import com.example.core.commands.Product_Core;
import com.example.core.events.OrderApprovedEvent;
import com.example.core.events.ProductReservedEvent;
import com.example.manage_inventory_service.command.rest.Product;
import com.example.manage_inventory_service.core.events.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;


@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private String userName;
    private ArrayList<Product_Core> orders;
    private String orderDate;
    private double totalPrice;
    private OrderStatus orderStatus;
    private String email;


    public OrderAggregate(){}


    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(createOrderCommand.getOrderId())
                .userName(createOrderCommand.getUserName())
                .orders(createOrderCommand.getOrders())
                .orderDate(createOrderCommand.getOrderDate())
                .totalPrice(createOrderCommand.getTotalPrice())
                .email(createOrderCommand.getEmail())
                .build();

        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.getOrderId();
        this.userName = orderCreatedEvent.getUserName();
        this.orders = orderCreatedEvent.getOrders();
        this.orderDate = orderCreatedEvent.getOrderDate();
        this.totalPrice = orderCreatedEvent.getTotalPrice();
        this.email = orderCreatedEvent.getEmail();
    }


// ย้ายไปใน Online Service
//    @CommandHandler
//    public void handle(ApproveOrderCommand approveOrderCommand){
//        OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent(approveOrderCommand.getOrderId());
//        AggregateLifecycle.apply(orderApprovedEvent);
//    }
//
//    @EventSourcingHandler
//    public void on(OrderApprovedEvent orderApprovedEvent){
//        this.orderStatus = orderApprovedEvent.getOrderStatus();
//    }







}
