package com.sweetcrust.team10_bakery.order.application.events;

import org.springframework.stereotype.Service;

import com.sweetcrust.team10_bakery.shared.infrastructure.adapter.email.MockEmailAdapter;
import com.sweetcrust.team10_bakery.shared.utils.Event;
import com.sweetcrust.team10_bakery.shared.utils.Observer;

import jakarta.annotation.PostConstruct;

@Service
public class OrderEventObserver implements Observer {

    private OrderEventPublisher orderEventPublisher;
    private MockEmailAdapter emailAdapter;

    public OrderEventObserver(OrderEventPublisher orderEventPublisher, MockEmailAdapter emailAdapter) {
        this.orderEventPublisher = orderEventPublisher;
        this.emailAdapter = emailAdapter;
    }

    @PostConstruct
    public void init() {
        orderEventPublisher.subscribe(this);
    }

    @Override
    public void onEvent(Event event) {
        switch (event) {
            case OrderCreatedEvent orderCreatedEvent -> {

                emailAdapter.sendEmail(
                    orderCreatedEvent.getRecipientEmail(),
                    "New order received: " + orderCreatedEvent.getOrder().getOrderId(),
                    "Order " + orderCreatedEvent.getOrder().getOrderId() + " has been placed. Requested delivery: "
                        + orderCreatedEvent.getOrder().getRequestedDeliveryDate()
                );

                emailAdapter.sendEmail(
                    orderCreatedEvent.getSenderEmail(),
                    "Order placed: " + orderCreatedEvent.getOrder().getOrderId(),
                    "Your order " + orderCreatedEvent.getOrder().getOrderId() + " has been placed and will be delivered at: "
                        + orderCreatedEvent.getOrder().getRequestedDeliveryDate()
                );
            }

            case OrderCancelledEvent orderCancelledEvent -> {

                emailAdapter.sendEmail(
                    orderCancelledEvent.getRecipientEmail(),
                    "Order cancelled: " + orderCancelledEvent.getOrder().getOrderId(),
                    "Order " + orderCancelledEvent.getOrder().getOrderId() + " was cancelled. Original requested delivery: "
                        + orderCancelledEvent.getOrder().getRequestedDeliveryDate()
                );

                emailAdapter.sendEmail(
                    orderCancelledEvent.getSenderEmail(),
                    "Order cancellation confirmed: " + orderCancelledEvent.getOrder().getOrderId(),
                    "Your cancellation of order " + orderCancelledEvent.getOrder().getOrderId() + " has been processed."
                );
            }

            default -> throw new IllegalArgumentException("Unknown event type: " + event.getClass());
        }
    }

}