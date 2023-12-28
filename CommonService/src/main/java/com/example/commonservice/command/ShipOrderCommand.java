package com.example.commonservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.persistence.Table;

@Data
@Builder
public class ShipOrderCommand
{

    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
}
