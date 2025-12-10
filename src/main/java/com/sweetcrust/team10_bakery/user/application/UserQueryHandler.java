package com.sweetcrust.team10_bakery.user.application;

import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserQueryHandler {

  private final UserRepository userRepository;

  public UserQueryHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(UserId userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new UserServiceException("user", "user not found"));
  }
}
