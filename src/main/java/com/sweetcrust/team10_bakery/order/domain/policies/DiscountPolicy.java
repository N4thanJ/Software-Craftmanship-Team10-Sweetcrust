package com.sweetcrust.team10_bakery.order.domain.policies;

import java.math.BigDecimal;

public interface DiscountPolicy {

    BigDecimal applyDiscount(BigDecimal subtotal);

    BigDecimal discountRate();
}
