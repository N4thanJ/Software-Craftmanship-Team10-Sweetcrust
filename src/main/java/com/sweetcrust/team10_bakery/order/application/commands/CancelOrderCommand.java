package com.sweetcrust.team10_bakery.order.application.commands;

import com.sweetcrust.team10_bakery.order.domain.valueobjects.OrderId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import jakarta.validation.constraints.NotNull;

public record CancelOrderCommand(@NotNull OrderId orderId, @NotNull UserId userId) {}
