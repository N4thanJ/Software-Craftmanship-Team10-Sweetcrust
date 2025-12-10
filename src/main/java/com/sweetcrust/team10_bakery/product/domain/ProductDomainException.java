package com.sweetcrust.team10_bakery.product.domain;

import com.sweetcrust.team10_bakery.shared.domain.DomainException;

public class ProductDomainException extends DomainException {
  public ProductDomainException(String field, String message) {
    super(field, message);
  }
}
