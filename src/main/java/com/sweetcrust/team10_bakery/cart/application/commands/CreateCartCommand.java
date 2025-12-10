package com.sweetcrust.team10_bakery.cart.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

public record CreateCartCommand(VariantId variantId, int quantity, UserId ownerId) {}
