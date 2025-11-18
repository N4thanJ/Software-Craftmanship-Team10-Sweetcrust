package com.sweetcrust.team10_bakery.order.domain.policies;

import java.time.LocalDate;

public interface DiscountCodePolicy extends DiscountPolicy {
    String code();

    LocalDate expiryDate();

    default boolean isExpired() {
        LocalDate expiry = expiryDate();
        if (expiry == null || LocalDate.MAX.equals(expiry)) return false;
        return LocalDate.now().isAfter(expiry);
    }
}
