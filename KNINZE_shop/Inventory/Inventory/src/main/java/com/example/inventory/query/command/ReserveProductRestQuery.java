package com.example.inventory.query.command;

import com.example.core.commands.Product_Core;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.ArrayList;
@Data
@Builder
public class ReserveProductRestQuery {
        @TargetAggregateIdentifier
        private final String inventoryId;
        private final String orderId;
        private final String userName;
        private final ArrayList<Product_Core> orders;
}
