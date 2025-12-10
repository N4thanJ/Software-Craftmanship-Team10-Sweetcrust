package com.sweetcrust.team10_bakery.order.application.events;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.shared.utils.Event;

public class OrderDeliveredEvent implements Event {
  private final Order order;
  private final String senderEmail;
  private final String recipientEmail;

  public OrderDeliveredEvent(Order order, String senderEmail, String recipientEmail) {
    this.order = order;
    this.senderEmail = senderEmail;
    this.recipientEmail = recipientEmail;
  }

  @Override
  public Order getOrder() {
    return order;
  }

  @Override
  public String getSenderEmail() {
    return senderEmail;
  }

  @Override
  public String getRecipientEmail() {
    return recipientEmail;
  }
}
