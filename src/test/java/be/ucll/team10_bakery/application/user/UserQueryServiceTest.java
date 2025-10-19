package be.ucll.team10_bakery.application.user;

import be.ucll.team10_bakery.user.application.UserQueryService;
import be.ucll.team10_bakery.user.domain.entities.User;
import be.ucll.team10_bakery.user.domain.valueobjects.UserRole;
import be.ucll.team10_bakery.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserQueryServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    void givenGetAllUsers_whenGettingAllUsers_thenAllUsersAreReturned() {
        // given
        List<User> allUsers = List.of(new User("bread-sheeran", "BreadS123!", "breadsheeran@sweetcrust.com", UserRole.BAKER), new User("toast-malone", "ToastM123!", "toastmalone@sweetcrust.com",  UserRole.CUSTOMER));
        when(userRepository.findAll()).thenReturn(allUsers);

        // when
        List<User> users = userQueryService.getAllUsers();

        // then
        assertNotNull(users);
        assertEquals(2,  users.size());
    }
}
