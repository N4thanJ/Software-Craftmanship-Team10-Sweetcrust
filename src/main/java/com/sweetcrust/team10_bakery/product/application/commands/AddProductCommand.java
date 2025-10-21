package com.sweetcrust.team10_bakery.product.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;

import java.math.BigDecimal;

public record AddProductCommand(
                String name,
                String description,
                BigDecimal basePrice,
                boolean available,
                VariantId variantId,
                CategoryId categoryId) {
}
