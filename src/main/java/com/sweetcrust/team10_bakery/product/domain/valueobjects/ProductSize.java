package com.sweetcrust.team10_bakery.product.domain.valueobjects;

import java.math.BigDecimal;

public enum ProductSize {
  MINI(BigDecimal.valueOf(0.3)),
  REGULAR(BigDecimal.valueOf(0.5)),
  LARGE(BigDecimal.valueOf(0.7));

  private final BigDecimal priceModifier;

  ProductSize(BigDecimal priceModifier) {
    this.priceModifier = priceModifier;
  }

  public BigDecimal getPriceModifier() {
    return priceModifier;
  }
}
