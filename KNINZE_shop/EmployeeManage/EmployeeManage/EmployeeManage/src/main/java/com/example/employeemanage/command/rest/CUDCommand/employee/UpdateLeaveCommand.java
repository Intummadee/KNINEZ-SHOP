package com.example.employeemanage.command.rest.CUDCommand.employee;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateLeaveCommand {
    @TargetAggregateIdentifier
    private final String aggregateId;
    private final Long employeeId;
    private final Integer leaveCount;

}
