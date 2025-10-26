package com.sweetcrust.team10_bakery.shared.domain.valueobjects;

import com.sweetcrust.team10_bakery.shared.domain.SharedDomainException;

public record Address(
        String street,
        String city,
        String postalCode,
        String country
) {
    public Address {
        if (street == null || street.isBlank()) {
            throw new SharedDomainException("Address", "street should not be blank or null");
        }
        if (city == null || city.isBlank()) {
            throw new SharedDomainException("Address", "city should not be blank or null");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new SharedDomainException("Address", "postalCode should not be blank or null");
        }
        if (country == null || country.isBlank()) {
            throw new SharedDomainException("Address", "country should not be blank or null");
        }
    }
}
