package com.sweetcrust.team10_bakery.shared.utils;

import com.sweetcrust.team10_bakery.order.domain.entities.Order;

public interface Event {
  String getSenderEmail();

  String getRecipientEmail();

  Order getOrder();
}
