package com.sweetcrust.team10_bakery.shop.application.commands;

import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopAddress;

public record AddShopCommand(
        String name,
        ShopAddress address,
        String email,
        CountryCode countryCode
) {
}
