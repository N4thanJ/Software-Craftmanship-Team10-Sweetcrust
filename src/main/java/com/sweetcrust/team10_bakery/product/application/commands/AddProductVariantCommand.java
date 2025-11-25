package com.sweetcrust.team10_bakery.product.application.commands;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.math.BigDecimal;

public record AddProductVariantCommand(
        ProductSize size,
        String variantName,
        BigDecimal priceModifier,
        UserId userId
) { }
