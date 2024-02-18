package com.example.core.commands;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private final String onlineId;
    private final String orderId;
    private final double totalPrice;



}
