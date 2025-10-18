package be.ucll.team10_bakery.user.presentation;

import be.ucll.team10_bakery.user.application.UserCommandService;
import be.ucll.team10_bakery.user.application.UserQueryService;
import be.ucll.team10_bakery.user.application.commands.AddUserCommand;
import be.ucll.team10_bakery.user.domain.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AddUserCommand addUserCommand) {
        userCommandService.registerUser(addUserCommand);
        return ResponseEntity.ok().build();
    }






}
