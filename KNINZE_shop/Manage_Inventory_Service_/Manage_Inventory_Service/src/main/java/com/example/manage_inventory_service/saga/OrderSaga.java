package com.example.manage_inventory_service.saga;



import com.example.core.commands.*;

import com.example.core.compensation.command.CancelProductReservationCommand;
import com.example.core.compensation.command.UndoAmountProductCommand;
import com.example.core.events.OrderApprovedEvent;
import com.example.core.events.ProductReservedEvent;
import com.example.core.events.UpdateCustomerEvent;
import com.example.core.query.AddHistoryCustomerQuery;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.core.query.HistoryCore;

import com.example.manage_inventory_service.core.events.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;


import java.util.ArrayList;
import java.util.UUID;

@Saga
public class OrderSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;






    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){
        // Saga get Event not Command
        System.out.println("OrderCreatedEvent ใน Saga จะส่งไป Serviceคลัง เพื่อตรวจสอบจำนวนสินค้า");


//        ตรงนี้คือ เอาไปเช็กใน Service คลัง ถ้าได้ true คือจะไปแก้ประวัติใน Customer
        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = FetchUserPaymentDetailsQuery.builder()
                .userName(orderCreatedEvent.getUserName())
                .orderId(orderCreatedEvent.getOrderId())
                .orders(orderCreatedEvent.getOrders())
                .build();
        boolean result;
        try {
            result = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(Boolean.class)).join();
            System.out.println("สินค้ามีพอจ้า");

            System.out.println(orderCreatedEvent.getTotalPrice());

            ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                    .inventoryId(UUID.randomUUID().toString())
                    .orderId(orderCreatedEvent.getOrderId())
                    .userName(orderCreatedEvent.getUserName())
                    .orders(orderCreatedEvent.getOrders())
                    .totalPrice(orderCreatedEvent.getTotalPrice())
                    .email(orderCreatedEvent.getEmail())
                    .build();


            commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
                if(commandResultMessage.isExceptional()){
                    //   กรณี product มันหมดในคลังแล้ว , Start a compensating transaction
                    System.out.println("ERROR2");
                    canProductReservation(orderCreatedEvent, commandResultMessage.exceptionResult().getMessage());
                }
            });


        }catch (Exception exception){
            result = false;
            System.out.println("สินค้าไม่พอ!!!");
            canProductReservation(orderCreatedEvent, "สินค้าในคลังไม่พอ");
        }
    }


    private void canProductReservation(OrderCreatedEvent orderCreatedEvent, String reason){
//        เป็น ฟังชัน เพื่อสร้าง command ไปใน Online Service เพื่อบอกให้รู้ว่า จำนวนสินค้านั้นมีไม่พอ
        System.out.println("เป็น ฟังชัน เพื่อสร้าง command ไปใน Online Service เพื่อบอกให้รู้ว่า จำนวนสินค้านั้นมีไม่พอ ");
        CancelProductReservationCommand cancelProductReservationCommand = CancelProductReservationCommand.builder()
                .onlineId(UUID.randomUUID().toString())
                .userName(orderCreatedEvent.getUserName())
                .orderId(orderCreatedEvent.getOrderId())
                .orders(orderCreatedEvent.getOrders())
                .reason(reason)
                .build();
        // onlineId , userName , orderId , orders
        commandGateway.send(cancelProductReservationCommand);
    }


    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent){
        System.out.println("Saga: กรณี product ไม่หมด user จะสามารถไปจ่ายเงินได้ โดยไปเพิ่มordersในCustomer Service");

        AddHistoryCustomerQuery addHistoryCustomerQuery = AddHistoryCustomerQuery.builder()
                .userName(productReservedEvent.getUserName())
                .orderId(productReservedEvent.getOrderId())
                .orders(productReservedEvent.getOrders())
                .totalPrice(productReservedEvent.getTotalPrice())
                .email(productReservedEvent.getEmail())
                .build();
        HistoryCore result;


            result = queryGateway.query(addHistoryCustomerQuery, ResponseTypes.instanceOf(HistoryCore.class)).join();
            System.out.println("อัพเดตHistory ordersของCustomerเรียบร้อย");
            System.out.println("result : " + result);






        UpdateCustomerCommand updateCustomerCommand = UpdateCustomerCommand.builder()
                .historyId(result.get_id())
                .userName(result.getUserName())
                .orders(result.getOrders())
                .orderId(result.getOrderId())
                .email(result.getEmail())
                .totalPrice(productReservedEvent.getTotalPrice())
                .build();
        commandGateway.send(updateCustomerCommand, (commandMessage, commandResultMessage) -> {
            if(commandResultMessage.isExceptional()){
//               ไปใน Customer Service
                System.out.println("กลับเข้ามาใน Saga หลังจากไป Customer มา");
            }
        });

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(UpdateCustomerEvent updateCustomerEvent){
        System.out.println("จาก Customer มาใน UpdateCustomerEvent ที่อยู่ใน Saga แล้ว ซึ่งเราจะ Approveตรงนี้ ");
//        ตรงนี้น่าจะต้องไปติดต่อกับ สั่งซื้อออนไลน์ด้วย ถ้าการสั่งซื้อมันสำเร็จ โดยให้สั่งซื้อมี aggregateรับ commandตัวนี้ด้วย
        ApproveOrderCommand approveOrderCommand = ApproveOrderCommand.builder()
                .onlineId(UUID.randomUUID().toString())
                .orderId(updateCustomerEvent.getOrderId())
                .totalPrice(updateCustomerEvent.getTotalPrice())
                .build();
        commandGateway.send(approveOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent){
        System.out.println("update Statusจาก ONline Service เสร็จแล้ว เข้าEventชื่อ OrderApprovedEvent ที่อยู่ใน Saga");
        System.out.println("จบขั้นตอน Saga แบบ Success เท่านี้");
//        onlineId ,orderId ,orderStatus

    }





}
