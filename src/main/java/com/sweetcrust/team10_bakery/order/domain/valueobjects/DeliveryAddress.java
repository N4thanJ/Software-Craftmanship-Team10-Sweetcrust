package com.sweetcrust.team10_bakery.order.domain.valueobjects;

import com.sweetcrust.team10_bakery.order.domain.OrderDomainException;

public record DeliveryAddress(
        String street,
        String city,
        String postalCode,
        String country
) {
    public DeliveryAddress {
        if (street == null || street.isBlank()) {
            throw new OrderDomainException("deliveryAddress", "street should not be blank or null");
        }
        if (city == null || city.isBlank()) {
            throw new OrderDomainException("deliveryAddress", "city should not be blank or null");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new OrderDomainException("deliveryAddress", "postalCode should not be blank or null");
        }
        if (country == null || country.isBlank()) {
            throw new OrderDomainException("deliveryAddress", "country should not be blank or null");
        }
        if (!postalCode.matches("\\d{3,10}")) {
            throw new OrderDomainException("deliveryAddress", "valid postalCode are 3-10 numbers");
        }
    }
}
