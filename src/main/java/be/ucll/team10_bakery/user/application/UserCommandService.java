package be.ucll.team10_bakery.user.application;

import be.ucll.team10_bakery.user.application.commands.AddUserCommand;
import be.ucll.team10_bakery.user.domain.entities.User;
import be.ucll.team10_bakery.user.domain.valueobjects.UserRole;
import be.ucll.team10_bakery.user.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCommandService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserCommandService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(AddUserCommand addUserCommand) {
        boolean exists = userRepository.existsByEmail(addUserCommand.email());
        if (exists) {
            throw new UserServiceException("email", "user with email already exists");
        }

        String hashedPassword =  bCryptPasswordEncoder.encode(addUserCommand.password());

        UserRole role;

        try {
            role = UserRole.valueOf(addUserCommand.role().toUpperCase());
        } catch  (IllegalArgumentException e) {
            throw new  UserServiceException("role", "invalid role: " +  addUserCommand.role() + " available roles: ADMIN, BAKER, CUSTOMER");
        }

        User user = new User(
                addUserCommand.username(),
                hashedPassword,
                addUserCommand.email(),
                role
        );

        userRepository.save(user);
    }
}
