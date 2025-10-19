package be.ucll.team10_bakery.application.user;

import be.ucll.team10_bakery.user.application.UserCommandService;
import be.ucll.team10_bakery.user.application.UserServiceException;
import be.ucll.team10_bakery.user.application.commands.AddUserCommand;
import be.ucll.team10_bakery.user.domain.entities.User;
import be.ucll.team10_bakery.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserCommandServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    void givenValidData_whenRegisterUser_thenUserIsCreated() {
        // given
        AddUserCommand addUserCommand = new AddUserCommand("bread-sheeran", "breadsheeran@sweetcrust.com", "BreadS123!", "BAKER");

        // when
        userCommandService.registerUser(addUserCommand);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void givenAlreadyRegisteredEmail_whenRegisterUser_thenExceptionIsThrown() {
        // given
        AddUserCommand addUserCommand = new AddUserCommand("bread-sheeran", "breadsheeran@sweetcrust.com",  "BreadS123!", "BAKER");
        when(userRepository.existsByEmail(addUserCommand.email())).thenReturn(true);

        // when
        UserServiceException exception = assertThrows(UserServiceException.class, ()  -> userCommandService.registerUser(addUserCommand));

        // then
        assertEquals("email", exception.getField());
        assertEquals("user with email already exists", exception.getMessage());
    }

    @Test
    void givenAlreadyRegisteredUsername_whenRegisterUser_thenExceptionIsThrown() {
        // given
        AddUserCommand addUserCommand = new AddUserCommand("bread-sheeran", "breadsheeran@sweetcrust.com",  "BreadS123!", "BAKER");
        when(userRepository.existsByUsername(addUserCommand.username())).thenReturn(true);

        // when
        UserServiceException exception = assertThrows(UserServiceException.class, ()  -> userCommandService.registerUser(addUserCommand));

        // then
        assertEquals("username", exception.getField());
        assertEquals("user with username already exists", exception.getMessage());
    }
}
