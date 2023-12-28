package com.ashutov.paymentservice.command.api.events;

import com.ashutov.paymentservice.command.api.data.Payment;
import com.ashutov.paymentservice.command.api.data.PaymentRepository;
import com.example.commonservice.events.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentEventHandler {

    private final PaymentRepository paymentRepository;

    public PaymentEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        var payment = Payment.builder()
                .paymentId(paymentProcessedEvent.getPaymentId())
                .orderId(paymentProcessedEvent.getOrderId())
                .paymentStatus("COMPLETED")
                .timeStamp(new Date())
                .build();

        paymentRepository.save(payment);
    }

}
