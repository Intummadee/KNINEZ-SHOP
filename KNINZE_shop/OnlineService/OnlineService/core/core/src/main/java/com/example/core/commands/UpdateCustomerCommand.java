package com.example.core.commands;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.ArrayList;

@Data
@Builder
public class UpdateCustomerCommand {
    @TargetAggregateIdentifier
    private final String customerAggregateIdentifier;
    private final String historyId;
    private final String userName;
    private final ArrayList<Product_Core> orders;
    private final String orderId;
    private final double totalPrice;
    private final String email;

}
