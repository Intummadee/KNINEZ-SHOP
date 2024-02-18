package com.example.inventory.query;


import com.example.core.commands.Product_Core;
import com.example.core.compensation.command.UndoAmountProductCommand;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.inventory.core.inventory.InventoryEntity;
import com.example.inventory.core.inventory.InventoryService;
import com.example.inventory.query.command.ReserveProductRestQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryEventsHandler {

    private final InventoryService inventoryService;

    public InventoryEventsHandler(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }


    @QueryHandler
    public boolean findUserPaymentDetails(FetchUserPaymentDetailsQuery query){
        System.out.println("findUserPaymentDetails in query");

        boolean check;


        ReserveProductRestQuery reserveProductRestQuery = ReserveProductRestQuery.builder()
//        reserveProductRestQuery = inventoryId(มาจากการสุ่ม) , orderId, userName , orders
                .orderId(query.getOrderId())
                .userName(query.getUserName())
                .orders(query.getOrders())
                .build();

        if (inventoryService.processOrder(reserveProductRestQuery)) {
            System.out.println("yessss จำนวนสินค้ามีพอ");
            check = true;
        } else {
            System.out.println("สินค้าไม่เพียงพอ คำสั่งซื้อถูกปฏิเสธ");
            check = false;
            throw new RuntimeException("สินค้าไม่เพียงพอ คำสั่งซื้อถูกปฏิเสธ");
        }

        return check;
    }





}
