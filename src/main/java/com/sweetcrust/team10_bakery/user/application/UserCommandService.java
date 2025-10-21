package com.sweetcrust.team10_bakery.user.application;

import com.sweetcrust.team10_bakery.user.application.commands.AddUserCommand;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCommandService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserCommandService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(AddUserCommand addUserCommand) {
        boolean existsEmail = userRepository.existsByEmail(addUserCommand.email());
        if (existsEmail) {
            throw new UserServiceException("email", "user with email already exists");
        }

        boolean existsUsername = userRepository.existsByUsername(addUserCommand.username());
        if (existsUsername) {
            throw new UserServiceException("username", "user with username already exists");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(addUserCommand.password());

        UserRole role;

        try {
            role = UserRole.valueOf(addUserCommand.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UserServiceException("role", "invalid role: " + addUserCommand.role() + " available roles: ADMIN, BAKER, CUSTOMER");
        }

        User user = new User(
                addUserCommand.username(),
                hashedPassword,
                addUserCommand.email(),
                role
        );

        return userRepository.save(user);
    }
}
