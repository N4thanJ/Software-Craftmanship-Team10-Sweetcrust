package com.sweetcrust.team10_bakery.order.application.events;

import com.sweetcrust.team10_bakery.shared.infrastructure.adapter.email.MockEmailAdapter;
import com.sweetcrust.team10_bakery.shared.utils.Event;
import com.sweetcrust.team10_bakery.shared.utils.Observer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class OrderEventObserver implements Observer {

  private OrderEventPublisher orderEventPublisher;
  private MockEmailAdapter emailAdapter;

  public OrderEventObserver(
      OrderEventPublisher orderEventPublisher, MockEmailAdapter emailAdapter) {
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
            "Order "
                + orderCreatedEvent.getOrder().getOrderId()
                + " has been placed. Requested delivery: "
                + orderCreatedEvent.getOrder().getRequestedDeliveryDate());

        emailAdapter.sendEmail(
            orderCreatedEvent.getSenderEmail(),
            "Order placed: " + orderCreatedEvent.getOrder().getOrderId(),
            "Your order "
                + orderCreatedEvent.getOrder().getOrderId()
                + " has been placed and will be delivered at: "
                + orderCreatedEvent.getOrder().getRequestedDeliveryDate());
      }

      case OrderCancelledEvent orderCancelledEvent -> {
        emailAdapter.sendEmail(
            orderCancelledEvent.getRecipientEmail(),
            "Order cancelled: " + orderCancelledEvent.getOrder().getOrderId(),
            "Order "
                + orderCancelledEvent.getOrder().getOrderId()
                + " was cancelled. Original requested delivery: "
                + orderCancelledEvent.getOrder().getRequestedDeliveryDate());

        emailAdapter.sendEmail(
            orderCancelledEvent.getSenderEmail(),
            "Order cancellation confirmed: " + orderCancelledEvent.getOrder().getOrderId(),
            "Your cancellation of order "
                + orderCancelledEvent.getOrder().getOrderId()
                + " has been processed.");
      }

        case OrderShippedEvent orderShippedEvent -> {
            emailAdapter.sendEmail(
                orderShippedEvent.getRecipientEmail(),
                "Order shipped",
                "Your order with id" + orderShippedEvent.getOrder().getOrderId() + " has been shipped and is on its way!");

            emailAdapter.sendEmail(
                orderShippedEvent.getSenderEmail(),
                "Order shipped notification sent",
                "A shipping notification has been sent to the customer.");
        }

        case OrderConfirmedEvent orderConfirmedEvent -> {
            emailAdapter.sendEmail(
                orderConfirmedEvent.getRecipientEmail(),
                "Order confirmed",
                "Your order with id" + orderConfirmedEvent.getOrder().getOrderId() + " has been confirmed and is being processed!");

            emailAdapter.sendEmail(
                orderConfirmedEvent.getSenderEmail(),
                "Order confirmation sent",
                "An order confirmation has been sent to the customer.");
        }

        case OrderDeliveredEvent orderDeliveredEvent -> {
            emailAdapter.sendEmail(
                orderDeliveredEvent.getRecipientEmail(),
                "Order delivered",
                "Your order has been delivered. Enjoy your purchase!");

            emailAdapter.sendEmail(
                orderDeliveredEvent.getSenderEmail(),
                "Order delivered",
                "Order" + orderDeliveredEvent.getOrder().getOrderId() + " has been delivered.");
        }

      default -> throw new IllegalArgumentException("Unknown event type: " + event.getClass());
    }
  }
}
