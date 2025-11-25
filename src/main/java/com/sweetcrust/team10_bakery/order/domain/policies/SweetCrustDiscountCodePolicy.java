package com.sweetcrust.team10_bakery.order.domain.policies;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class SweetCrustDiscountCodePolicy implements DiscountCodePolicy {

    private static final BigDecimal RATE = BigDecimal.valueOf(0.10);

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
        return "SWEETCRUST10";
    }

    @Override
    public LocalDate expiryDate() {
        return LocalDate.MAX;
    }
}
