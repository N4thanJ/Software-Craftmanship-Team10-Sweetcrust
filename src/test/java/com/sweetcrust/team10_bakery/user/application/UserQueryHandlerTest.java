package com.sweetcrust.team10_bakery.user.application;

import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserQueryHandlerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserQueryHandler userQueryHandler;

    @Test
    void givenGetAllUsers_whenGettingAllUsers_thenAllUsersAreReturned() {
        // given
        List<User> allUsers = List.of(new User("bread-sheeran", "BreadS123!", "breadsheeran@sweetcrust.com", UserRole.BAKER), new User("toast-malone", "ToastM123!", "toastmalone@sweetcrust.com", UserRole.CUSTOMER));
        when(userRepository.findAll()).thenReturn(allUsers);

        // when
        List<User> users = userQueryHandler.getAllUsers();

        // then
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void givenExistingUserId_whenGetById_thenUserWithIdIsReturned() {
        // given
        UserId userId = new UserId(UUID.randomUUID());
        User user = new User("bread-sheeran", "BreadS123!", "breadsheeran@sweetcrust.com", UserRole.BAKER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User foundUser = userQueryHandler.getUserById(userId);

        // then
        assertEquals(user, foundUser);
    }

    @Test
    void givenInexistingUserId_whenGetById_thenExceptionisThrown() {
        // given
        UserId userId = new UserId(UUID.randomUUID());

        // when
        UserServiceException exception = assertThrows(UserServiceException.class, () -> userQueryHandler.getUserById(userId));

        // then
        assertEquals("user", exception.getField());
        assertEquals("user not found", exception.getMessage());
    }
}
