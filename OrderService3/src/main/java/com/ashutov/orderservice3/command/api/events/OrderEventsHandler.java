package com.ashutov.orderservice3.command.api.events;

import com.ashutov.orderservice3.command.api.data.Order;
import com.ashutov.orderservice3.command.api.data.OrderRepository;
import com.example.commonservice.events.OrderCancelledEvent;
import com.example.commonservice.events.OrderCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventsHandler {

    private final OrderRepository orderRepository;

    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent: {}", event);
        var order = new Order();
        BeanUtils.copyProperties(event, order);
        orderRepository.save(order);
        orderRepository.flush();
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent: {}", event);

        Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        log.info("OrderCreatedEvent: {}", event);

        Order order
                = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }
}

