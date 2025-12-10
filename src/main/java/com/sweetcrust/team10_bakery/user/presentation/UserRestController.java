package com.sweetcrust.team10_bakery.user.presentation;

import com.sweetcrust.team10_bakery.user.application.UserCommandHandler;
import com.sweetcrust.team10_bakery.user.application.UserQueryHandler;
import com.sweetcrust.team10_bakery.user.application.commands.AddUserCommand;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(
    name = "User Management",
    description = "Endpoints related to user registration and user fetching")
public class UserRestController {

  private final UserCommandHandler userCommandHandler;
  private final UserQueryHandler userQueryHandler;

  public UserRestController(
      UserCommandHandler userCommandHandler, UserQueryHandler userQueryHandler) {
    this.userCommandHandler = userCommandHandler;
    this.userQueryHandler = userQueryHandler;
  }

  @GetMapping()
  public ResponseEntity<Iterable<User>> getAllUsers() {
    List<User> users = userQueryHandler.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUser(@PathVariable UUID userId) {
    User user = userQueryHandler.getUserById(new UserId(userId));
    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody AddUserCommand addUserCommand) {
    User user = userCommandHandler.registerUser(addUserCommand);
    return ResponseEntity.ok(user);
  }
}
