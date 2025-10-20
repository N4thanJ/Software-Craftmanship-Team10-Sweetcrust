package com.sweetcrust.team10_bakery.order.domain.valueobjects;

import org.springframework.util.Assert;

public record DeliveryAddress(
        String street,
        String city,
        String postalCode,
        String country
) {
    public DeliveryAddress {
        Assert.notNull(street, "street must not be null");
        Assert.notNull(city, "city must not be null");
        Assert.notNull(postalCode, "postalCode must not be null");
        Assert.notNull(country, "country must not be null");
    }
}
