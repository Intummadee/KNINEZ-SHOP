package com.example.core.events;


import com.example.core.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
@Data
public class OrderApprovedEvent {
    String onlineId;
    String orderId;
    double totalPrice;

    OrderStatus orderStatus = OrderStatus.APPROVED;

}
