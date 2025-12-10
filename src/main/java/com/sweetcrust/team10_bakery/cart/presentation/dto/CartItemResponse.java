package com.sweetcrust.team10_bakery.cart.presentation.dto;

import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartItemId;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import java.math.BigDecimal;

public record CartItemResponse(
    CartItemId cartItemId,
    VariantId variantId,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal totalPrice) {}
