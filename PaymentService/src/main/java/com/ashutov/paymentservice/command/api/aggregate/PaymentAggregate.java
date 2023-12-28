package com.ashutov.paymentservice.command.api.aggregate;

import com.example.commonservice.command.ValidatePaymentCommand;
import com.example.commonservice.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;



@Slf4j
@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        //Validate the payment details
        //publish the payment processed event
        log.info("Executing ValidatePaymentCommand for OrderId {}" +
                " and PaymentId: {}", validatePaymentCommand.getOrderId(),
                validatePaymentCommand.getPaymentId());

        var paymentProcessedEvent
                = new PaymentProcessedEvent(validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());

        AggregateLifecycle.apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }

}
