package com.sweetcrust.team10_bakery.inventory.domain;

import com.sweetcrust.team10_bakery.shared.domain.DomainException;

public class InventoryDomainException extends DomainException {

  public InventoryDomainException(String field, String message) {
    super(field, message);
  }
}
