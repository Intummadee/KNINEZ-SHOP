package com.example.core.compensation.event;

import com.example.core.commands.Product_Core;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.ArrayList;


@Data
@Builder
public class ProductReservationCancelledEvent {
//    กรณีถ้า สินค้ามีจำนววนไม่พอ จะเกิด eventตัวนี้ขึ้น

    private final String onlineId;
    private final String userName;
    private final String orderId;
    private final ArrayList<Product_Core> orders;
    private final String reason;

}
