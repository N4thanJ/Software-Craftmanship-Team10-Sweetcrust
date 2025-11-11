package com.sweetcrust.team10_bakery.product.presentation.dto;


import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDto(
        ProductId productId,
        String name,
        String description,
        BigDecimal basePrice,
        boolean available,
        CategoryId categoryId,
        List<ProductVariantResponseDto> variants
) {
}
