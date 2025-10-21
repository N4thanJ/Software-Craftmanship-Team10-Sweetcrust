package com.sweetcrust.team10_bakery.user.presentation;

import com.sweetcrust.team10_bakery.user.application.UserCommandService;
import com.sweetcrust.team10_bakery.user.application.UserQueryService;
import com.sweetcrust.team10_bakery.user.application.commands.AddUserCommand;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "User Management", description = "Endpoints related to user registration and user fetching")
public class UserRestController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Autowired
    public UserRestController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        List<User> users = userQueryService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId) {
        User user = userQueryService.getUserById(new UserId(userId));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AddUserCommand addUserCommand) {
        User user = userCommandService.registerUser(addUserCommand);
        return ResponseEntity.ok(user);
    }

}
