package com.sweetcrust.team10_bakery.order.application.queries;

import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderType;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import jakarta.validation.constraints.NotNull;

public record GetAllOrdersByUserIdQuery(@NotNull OrderType orderType, @NotNull UserId userId) {}
