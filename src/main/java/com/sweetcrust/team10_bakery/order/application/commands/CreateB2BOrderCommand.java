package com.sweetcrust.team10_bakery.order.application.commands;

import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

import java.time.LocalDateTime;

public record CreateB2BOrderCommand(
        LocalDateTime requestedDeliveryDate,
        ShopId orderingShopId,
        ShopId sourceShopId,
        UserId userId
) {
}
