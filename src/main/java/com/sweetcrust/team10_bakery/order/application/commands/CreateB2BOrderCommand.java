package com.sweetcrust.team10_bakery.order.application.commands;

import com.sweetcrust.team10_bakery.order.domain.valueobjects.*;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import java.time.LocalDateTime;

public record CreateB2BOrderCommand(
        OrderType orderType,
        LocalDateTime requestedDeliveryDate,
        ShopId orderingShopId,
        ShopId sourceShopId
) {
}
