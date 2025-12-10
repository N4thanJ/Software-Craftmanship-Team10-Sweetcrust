package com.sweetcrust.team10_bakery.order.domain.policies;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class HappyNewYearDiscountCodePolicy implements DiscountCodePolicy {

  private static final BigDecimal RATE = BigDecimal.valueOf(0.50);
  private static final LocalDate EXPIRY = LocalDate.of(2026, 1, 31);

  @Override
  public BigDecimal applyDiscount(BigDecimal subtotal) {
    if (subtotal == null) return BigDecimal.ZERO;
    return subtotal.subtract(subtotal.multiply(RATE));
  }

  @Override
  public BigDecimal discountRate() {
    return RATE;
  }

  @Override
  public String code() {
    return "HAPPYNEWYEAR50";
  }

  @Override
  public LocalDate expiryDate() {
    return EXPIRY;
  }
}
