package com.sweetcrust.team10_bakery.order.application.commands;

import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

public record ConfirmOrderCommand(OrderId orderId, ShopId sourceShopId, UserId sourceShopOwnerId) {}
