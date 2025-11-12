package com.sweetcrust.team10_bakery.product.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

import java.math.BigDecimal;
import java.util.List;

public record AddProductCommand(
        String name,
        String description,
        BigDecimal basePrice,
        boolean available,
        CategoryId categoryId,
        List<AddProductVariantCommand> variants,
        UserId userId
) { }
