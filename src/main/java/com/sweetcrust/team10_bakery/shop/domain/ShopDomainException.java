package com.sweetcrust.team10_bakery.shop.domain;

import com.sweetcrust.team10_bakery.shared.domain.DomainException;

public class ShopDomainException extends DomainException {

  public ShopDomainException(String field, String message) {
    super(field, message);
  }
}
