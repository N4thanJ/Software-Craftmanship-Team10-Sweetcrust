package com.sweetcrust.team10_bakery.user.application.commands;

public record AddUserCommand (
        String username,
        String email,
        String password,
        String role
) {
}
