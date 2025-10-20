package com.sweetcrust.team10_bakery.shop.domain.valueobjects;

import com.sweetcrust.team10_bakery.shop.domain.ShopDomainException;

public record ShopAddress(
        String street,
        String city,
        String postalCode,
        String country,
        CountryCode countryCode
) {
    public ShopAddress {
        if (street == null || street.isBlank()) {
            throw new ShopDomainException("shopAddress", "street should not be blank or null");
        }
        if (city == null || city.isBlank()) {
            throw new ShopDomainException("shopAddress", "city should not be blank or null");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new ShopDomainException("shopAddress", "postalCode should not be blank or null");
        }
        if (country == null || country.isBlank()) {
            throw new ShopDomainException("shopAddress", "country should not be blank or null");
        }
        if (countryCode == null) {
            throw new ShopDomainException("shopAddress", "countryCode should not be null");
        }
        if (!postalCode.matches("\\d{3,10}")) {
            throw new ShopDomainException("shopAddress", "valid postalCode are 3-10 numbers");
        }
    }
}
