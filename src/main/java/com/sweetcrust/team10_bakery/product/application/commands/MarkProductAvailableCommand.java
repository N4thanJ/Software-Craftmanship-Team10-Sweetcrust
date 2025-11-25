package com.sweetcrust.team10_bakery.product.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

public record MarkProductAvailableCommand(ProductId productId, UserId userId) {}
