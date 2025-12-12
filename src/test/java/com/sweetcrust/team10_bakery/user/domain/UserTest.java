package com.sweetcrust.team10_bakery.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private String defaultUsername;
    private String defaultPassword;
    private String defaultEmail;
    private UserRole defaultRole;

    @BeforeEach
    void setup() {
        defaultUsername = "muffin-man";
        defaultPassword = "Bread123!";
        defaultEmail = "muffin@sweetcrust.com";
        defaultRole = UserRole.CUSTOMER;
    }

    private User createUser() {
        return new User(defaultUsername, defaultPassword, defaultEmail, defaultRole);
    }

    private void expectFieldError(String field, String message, Runnable action) {
        UserDomainException ex = assertThrows(UserDomainException.class, action::run);
        assertEquals(field, ex.getField());
        assertEquals(message, ex.getMessage());
    }

    @Test
    void givenValidData_whenCreatingUser_thenUserIsCreated() {
        User user = createUser();

        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertEquals(defaultUsername, user.getUsername());
        assertEquals(defaultPassword, user.getPassword());
        assertEquals(defaultEmail, user.getEmail());
        assertEquals(defaultRole, user.getRole());
    }

    @Test
    void givenNullUsername_whenCreatingUser_thenThrowsException() {
        defaultUsername = null;
        expectFieldError("username", "username cannot be null or blank", this::createUser);
    }

    @Test
    void givenEmptyUsername_whenCreatingUser_thenThrowsException() {
        defaultUsername = "";
        expectFieldError("username", "username cannot be null or blank", this::createUser);
    }

    @Test
    void givenBlankUsername_whenCreatingUser_thenThrowsException() {
        defaultUsername = "   ";
        expectFieldError("username", "username cannot be null or blank", this::createUser);
    }

    @Test
    void givenNullPassword_whenCreatingUser_thenThrowsException() {
        defaultPassword = null;
        expectFieldError("password", "password cannot be null or blank", this::createUser);
    }

    @Test
    void givenEmptyPassword_whenCreatingUser_thenThrowsException() {
        defaultPassword = "";
        expectFieldError("password", "password cannot be null or blank", this::createUser);
    }

    @Test
    void givenBlankPassword_whenCreatingUser_thenThrowsException() {
        defaultPassword = "   ";
        expectFieldError("password", "password cannot be null or blank", this::createUser);
    }

    @Test
    void givenPasswordShorterThan8Characters_whenCreatingUser_thenThrowsException() {
        defaultPassword = "Short1!";
        expectFieldError("password", "password must be at least 8 characters", this::createUser);
    }

    @Test
    void givenPasswordWithoutLowerCase_whenCreatingUser_thenThrowsException() {
        defaultPassword = "MUFFINTOP123!";
        expectFieldError("password", "password must have 1 lower case letter", this::createUser);
    }

    @Test
    void givenPasswordWithoutUpperCase_whenCreatingUser_thenThrowsException() {
        defaultPassword = "croissant123!";
        expectFieldError("password", "password must have 1 upper case letter", this::createUser);
    }

    @Test
    void givenPasswordWithoutNumber_whenCreatingUser_thenThrowsException() {
        defaultPassword = "Baguette!";
        expectFieldError("password", "password must have 1 number", this::createUser);
    }

    @Test
    void givenPasswordWithoutSpecialCharacter_whenCreatingUser_thenThrowsException() {
        defaultPassword = "Cinnamon123";
        expectFieldError("password", "password must have at least one special character", this::createUser);
    }

    @Test
    void givenValidPasswordWithAtSymbol_whenCreatingUser_thenUserIsCreated() {
        defaultPassword = "Croissant123@#";

        User user = createUser();

        assertNotNull(user);
        assertEquals(defaultPassword, user.getPassword());
    }

    @Test
    void givenNullEmail_whenCreatingUser_thenThrowsException() {
        defaultEmail = null;
        expectFieldError("email", "email cannot be null or blank", this::createUser);
    }

    @Test
    void givenEmptyEmail_whenCreatingUser_thenThrowsException() {
        defaultEmail = "";
        expectFieldError("email", "email cannot be null or blank", this::createUser);
    }

    @Test
    void givenBlankEmail_whenCreatingUser_thenThrowsException() {
        defaultEmail = "   ";
        expectFieldError("email", "email cannot be null or blank", this::createUser);
    }

    @Test
    void givenEmailWithoutAtSymbol_whenCreatingUser_thenThrowsException() {
        defaultEmail = "invalidemail";
        expectFieldError("email", "invalid email", this::createUser);
    }

    @Test
    void givenEmailWithoutUsername_whenCreatingUser_thenThrowsException() {
        defaultEmail = "@example.com";
        expectFieldError("email", "invalid email", this::createUser);
    }

    @Test
    void givenEmailWithoutDomain_whenCreatingUser_thenThrowsException() {
        defaultEmail = "invalid@";
        expectFieldError("email", "invalid email", this::createUser);
    }

    @Test
    void givenEmailWithoutTopLevelDomain_whenCreatingUser_thenThrowsException() {
        defaultEmail = "invalid@example";
        expectFieldError("email", "invalid email", this::createUser);
    }

    @Test
    void givenEmailWithSpaces_whenCreatingUser_thenThrowsException() {
        defaultEmail = "invalid email@example.com";
        expectFieldError("email", "invalid email", this::createUser);
    }

    @Test
    void givenNullRole_whenCreatingUser_thenThrowsException() {
        defaultRole = null;
        expectFieldError("role", "role cannot be null", this::createUser);
    }
}
