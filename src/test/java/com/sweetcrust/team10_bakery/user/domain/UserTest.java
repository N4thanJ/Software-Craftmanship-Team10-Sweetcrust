package com.sweetcrust.team10_bakery.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void givenValidData_whenCreatingUser_thenUserIsCreated() {
        // given
        String username = "muffin-man";
        String password = "Bread123!";
        String email = "muffin@sweetcrust.com";
        UserRole role = UserRole.CUSTOMER;

        // when
        User user = new User(username, password, email, role);

        // then
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
    }

    @Test
    void givenNullUsername_whenCreatingUser_thenThrowsException() {
        // given
        String password = "Baguette1!";
        String email = "bready@sweetcrust.com";
        UserRole role = UserRole.CUSTOMER;

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User(null, password, email, role));

        // then
        assertEquals("username", exception.getField());
        assertEquals("username cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenEmptyUsername_whenCreatingUser_thenThrowsException() {
        // given
        String password = "Baguette1!";
        String email = "bready@sweetcrust.com";
        UserRole role = UserRole.CUSTOMER;

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("", password, email, role));

        // then
        assertEquals("username", exception.getField());
        assertEquals("username cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankUsername_whenCreatingUser_thenThrowsException() {
        // given
        String password = "Baguette1!";
        String email = "bready@sweetcrust.com";
        UserRole role = UserRole.CUSTOMER;

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("   ", password, email, role));

        // then
        assertEquals("username", exception.getField());
        assertEquals("username cannot be null or blank", exception.getMessage());
    }


    @Test
    void givenNullPassword_whenCreatingUser_thenThrowsException() {
        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("captain-crumb", null, "crumb@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenEmptyPassword_whenCreatingUser_thenThrowsException() {
        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("captain-crumb", "", "crumb@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankPassword_whenCreatingUser_thenThrowsException() {
        // when 
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("captain-crumb", "   ", "crumb@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenPasswordShorterThan8Characters_whenCreatingUser_thenThrowsException() {
        // given
        String password = "Short1!";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("flourpower", password, "flour@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password must be at least 8 characters", exception.getMessage());
    }

    @Test
    void givenPasswordWithoutLowerCase_whenCreatingUser_thenThrowsException() {
        // given
        String password = "MUFFINTOP123!";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("muffin-man", password, "muffin@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password must have 1 lower case letter", exception.getMessage());
    }

    @Test
    void givenPasswordWithoutUpperCase_whenCreatingUser_thenThrowsException() {
        // given
        String password = "croissant123!";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("buttercup", password, "butter@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password must have 1 upper case letter", exception.getMessage());
    }

    @Test
    void givenPasswordWithoutNumber_whenCreatingUser_thenThrowsException() {
        // given
        String password = "Baguette!";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("baguetteboss", password, "baguette@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password must have 1 number", exception.getMessage());
    }

    @Test
    void givenPasswordWithoutSpecialCharacter_whenCreatingUser_thenThrowsException() {
        // given
        String password = "Cinnamon123";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bagelstein", password, "bagelstein@sweetcrust.com", UserRole.CUSTOMER));

        // then
        assertEquals("password", exception.getField());
        assertEquals("password must have at least one special character", exception.getMessage());
    }

    @Test
    void givenValidPasswordWithAtSymbol_whenCreatingUser_thenUserIsCreated() {
        // given
        String password = "Croissant123@#";

        // when
        User user = new User("loaf-vader", password, "loaf@sweetcrust.com", UserRole.CUSTOMER);

        // then
        assertNotNull(user);
        assertEquals(password, user.getPassword());
    }

    @Test
    void givenNullEmail_whenCreatingUser_thenThrowsException() {
        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Pumpernickel7!", null, UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("email cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenEmptyEmail_whenCreatingUser_thenThrowsException() {
        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Pumpernickel7!", "", UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("email cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenBlankEmail_whenCreatingUser_thenThrowsException() {
        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Pumpernickel7!", "   ", UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("email cannot be null or blank", exception.getMessage());
    }

    @Test
    void givenEmailWithoutAtSymbol_whenCreatingUser_thenThrowsException() {
        // given
        String email = "invalidemail";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Levain123!", email, UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenEmailWithoutUsername_whenCreatingUser_thenThrowsException() {
        // given
        String email = "@example.com";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Levain123!", email, UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenEmailWithoutDomain_whenCreatingUser_thenThrowsException() {
        // given
        String email = "invalid@";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Levain123!", email, UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenEmailWithoutTopLevelDomain_whenCreatingUser_thenThrowsException() {
        // given
        String email = "invalid@example";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Levain123!", email, UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenEmailWithSpaces_whenCreatingUser_thenThrowsException() {
        // given
        String email = "invalid example@example.com";

        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("bread-sheeran", "Levain123!", email, UserRole.CUSTOMER));

        // then
        assertEquals("email", exception.getField());
        assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void givenNullRole_whenCreatingUser_thenThrowsException() {
        // when
        UserDomainException exception = assertThrows(UserDomainException.class,
                () -> new User("cupcake-queen", "Frosting6^", "cupcake@sweetcrust.com", null));

        // then
        assertEquals("role", exception.getField());
        assertEquals("role cannot be null", exception.getMessage());
    }
}