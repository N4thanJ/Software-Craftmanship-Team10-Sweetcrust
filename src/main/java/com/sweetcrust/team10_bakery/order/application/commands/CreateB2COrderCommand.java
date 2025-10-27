package com.sweetcrust.team10_bakery.order.application.commands;

import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.time.LocalDateTime;

public record CreateB2COrderCommand(
        Address deliveryAddress,
        LocalDateTime requestedDeliveryDate,
        UserId customerId
) {
}
