package com.sweetcrust.team10_bakery.order.domain.policies;

import java.math.BigDecimal;

public interface DiscountPolicy {

    // total after applying discount
    BigDecimal applyDiscount(BigDecimal subtotal);

    // discount rate
    BigDecimal discountRate();
}
