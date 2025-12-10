package com.sweetcrust.team10_bakery.product.presentation.dto;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import java.math.BigDecimal;

public record ProductVariantResponseDto(
    VariantId variantId, ProductSize size, String variantName, BigDecimal priceModifier) {}
