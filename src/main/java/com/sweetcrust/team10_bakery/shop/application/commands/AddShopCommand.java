package com.sweetcrust.team10_bakery.shop.application.commands;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

public record AddShopCommand(
        String name,
        Address shopAddress,
        String email,
        CountryCode countryCode,
        UserId userId
) {
}
