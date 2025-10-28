package com.sweetcrust.team10_bakery.cart.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;

public record AddCartItemCommand(
                ProductId productId,
                int quantity) {
}
