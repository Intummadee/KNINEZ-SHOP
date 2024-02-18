package com.example.core.events;


import com.example.core.commands.Product_Core;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class UpdateCustomerEvent {
    private final String customerAggregateIdentifier;
    private final String historyId;
    private final String userName;
    private final ArrayList<Product_Core> orders;
    private final String orderId;
    private final double totalPrice;
    private final String email;

}
