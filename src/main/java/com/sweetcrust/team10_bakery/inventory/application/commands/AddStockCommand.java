package com.sweetcrust.team10_bakery.inventory.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;

public record AddStockCommand(VariantId variantId) {

}
