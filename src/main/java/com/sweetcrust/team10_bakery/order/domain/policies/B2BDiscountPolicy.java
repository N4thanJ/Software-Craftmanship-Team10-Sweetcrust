package com.sweetcrust.team10_bakery.order.domain.policies;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class B2BDiscountPolicy implements DiscountPolicy{

    private final BigDecimal discountRate = new BigDecimal("0.21");

    @Override
    public BigDecimal applyDiscount(BigDecimal subtotal) {
        if (subtotal == null) return BigDecimal.ZERO;
        BigDecimal discount = subtotal.multiply(discountRate);
        return subtotal.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal discountRate() {
        return discountRate;
    }
}
