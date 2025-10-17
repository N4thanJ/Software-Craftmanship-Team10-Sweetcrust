package be.ucll.team10_bakery.user.application;

import be.ucll.team10_bakery.user.domain.entities.User;
import be.ucll.team10_bakery.user.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQueryService {

    private final UserRepository userRepository;

    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
