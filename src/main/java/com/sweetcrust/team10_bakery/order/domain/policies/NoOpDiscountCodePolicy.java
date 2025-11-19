package com.sweetcrust.team10_bakery.order.domain.policies;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class NoOpDiscountCodePolicy implements DiscountCodePolicy {
    private static final BigDecimal RATE = BigDecimal.ZERO;

    @Override
    public BigDecimal applyDiscount(BigDecimal subtotal) {
        return subtotal == null ? BigDecimal.ZERO : subtotal;
    }

    @Override
    public BigDecimal discountRate() {
        return RATE;
    }

    @Override
    public String code() {
        return "NO_DISCOUNT";
    }

    @Override
    public LocalDate expiryDate() {
        return LocalDate.MAX;
    }
}
