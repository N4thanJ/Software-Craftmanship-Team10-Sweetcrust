package com.sweetcrust.team10_bakery.shop.domain.valueobjects;

import com.sweetcrust.team10_bakery.shop.domain.ShopDomainException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public record CountryCode(String code) {

    private static final Set<String> validCodes = Arrays.stream(Locale.getISOCountries())
            .collect(Collectors.toSet());

    public CountryCode {
        if (code == null || code.isBlank()) {
            throw new ShopDomainException("countryCode", "countryCode should not be null or blank");
        }

        String upperCode = code.toUpperCase();

        if (!upperCode.matches("^[A-Z]{2}$")) {
            throw new ShopDomainException("countryCode", "countryCode should only contain alphanumeric characters");
        }

        if (!validCodes.contains(upperCode)) {
            throw new ShopDomainException("countryCode", "invalid country code");
        }

        code = upperCode;
    }
}
