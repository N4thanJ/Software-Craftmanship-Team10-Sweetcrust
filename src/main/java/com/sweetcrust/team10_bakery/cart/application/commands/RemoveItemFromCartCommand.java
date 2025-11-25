package com.sweetcrust.team10_bakery.cart.application.commands;

import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;

public record RemoveItemFromCartCommand(UserId ownerId, int quantity) {}
