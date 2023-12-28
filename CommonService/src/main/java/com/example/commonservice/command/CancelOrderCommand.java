package com.example.commonservice.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    String orderId;
    String orderStatus = "CANCELLED";
}
