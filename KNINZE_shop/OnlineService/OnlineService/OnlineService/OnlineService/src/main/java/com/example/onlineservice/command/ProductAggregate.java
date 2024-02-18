package com.example.onlineservice.command;


import com.example.core.OrderStatus;
import com.example.core.commands.ApproveOrderCommand;
import com.example.core.compensation.command.CancelProductReservationCommand;
import com.example.core.compensation.event.ProductReservationCancelledEvent;
import com.example.core.events.OrderApprovedEvent;
import com.example.onlineservice.command.rest.Product;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;

@Aggregate
public class ProductAggregate {


    @AggregateIdentifier
    private String onlineId;
    private String orderId;

    private String userName;
    private ArrayList<Product> orders;
    private String orderDate;
    private double totalPrice;
    private String reason;
    private OrderStatus orderStatus;




    public ProductAggregate(){}


    @CommandHandler
    public ProductAggregate(ApproveOrderCommand approveOrderCommand){
        System.out.println("กรณีสำเร็จ จะอัพเดตสเตตัสว่า คำสั่งซื้อนี้ สำเร็จ");



        OrderApprovedEvent orderApprovedEvent = OrderApprovedEvent.builder()
                .onlineId(approveOrderCommand.getOnlineId())
                .orderId(approveOrderCommand.getOrderId())
                .totalPrice(approveOrderCommand.getTotalPrice())
                .build();
        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent){
// orderId , orderStatus = OrderStatus.APPROVED จะได้ status Approve มา

        System.out.println("event ใน Online Service OrderApprovedEvent ;");
        System.out.println(orderApprovedEvent);
        this.onlineId = orderApprovedEvent.getOnlineId();
        this.orderId = orderApprovedEvent.getOrderId();
        this.orderStatus = orderApprovedEvent.getOrderStatus();
        this.totalPrice = orderApprovedEvent.getTotalPrice();



    }





    @CommandHandler
    public ProductAggregate(CancelProductReservationCommand cancelProductReservationCommand){
        System.out.println("เข้ามาใน Online Service กรณีจำนวนสินค้าไม่พอ reason : " + cancelProductReservationCommand.getReason());
        // cancelProductReservationCommand = onlineId , userName , orderId , orders , reason
        ProductReservationCancelledEvent productReservationCancelledEvent = ProductReservationCancelledEvent.builder()
                .onlineId(cancelProductReservationCommand.getOnlineId())
                .orders(cancelProductReservationCommand.getOrders())
                .userName(cancelProductReservationCommand.getUserName())
                .orderId(cancelProductReservationCommand.getOrderId())
                .reason(cancelProductReservationCommand.getReason())
                .build();
        AggregateLifecycle.apply(productReservationCancelledEvent);
    }

    @EventSourcingHandler
        public void on(ProductReservationCancelledEvent productReservationCancelledEvent){
            System.out.println("เข้า event เพื่ออัพเดต reason ชื่อ event : productReservationCancelledEvent");
            this.onlineId = productReservationCancelledEvent.getOnlineId();
            this.userName = productReservationCancelledEvent.getUserName();
            this.reason = productReservationCancelledEvent.getReason();
            this.orderStatus = OrderStatus.REJECTED;
    }



}