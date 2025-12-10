package com.sweetcrust.team10_bakery.cart.presentation.dto;

import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.util.List;

public record CartResponse(CartId cartId, UserId userId, List<CartItemResponse> items) {}
