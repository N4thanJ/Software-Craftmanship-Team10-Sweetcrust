package com.sweetcrust.team10_bakery.order.domain.policies;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ExpiredDiscountCodePolicy implements DiscountCodePolicy {

    private static final BigDecimal RATE = BigDecimal.valueOf(0.20); // 20%
    private static final LocalDate EXPIRY = LocalDate.of(2023, 12, 31);
    private static final String CODE = "EXPIRED20";

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
        return CODE;
    }

    @Override
    public LocalDate expiryDate() {
        return EXPIRY;
    }
}
