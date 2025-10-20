package com.sweetcrust.team10_bakery.user.domain.entities;

import com.sweetcrust.team10_bakery.user.domain.UserDomainException;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @EmbeddedId
    private UserId userId;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    protected User() {
    }

    public User(String username, String password, String email,  UserRole role) {
        this.userId = new  UserId();
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRole(role);
    }

    public UserId getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new UserDomainException("username", "username cannot be null or blank");
        }

        this.username = username;
    }

    public void setPassword(String password) {
        if  (password == null || password.isBlank()) {
            throw new UserDomainException("password", "password cannot be null or blank");
        }

        if (password.length() < 8) {
            throw new UserDomainException("password", "password must be at least 8 characters");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new UserDomainException("password", "password must have 1 lower case letter");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new UserDomainException("password", "password must have 1 upper case letter");
        }

        if (!password.matches(".*[0-9].*")) {
            throw new UserDomainException("password", "password must have 1 number");
        }

        if (!password.matches(".*[@#$%^&+=!].*")) {
            throw new UserDomainException("password", "password must have at least one special character");
        }

        this.password = password;
    }

    public void setRole(UserRole role) {
        if (role == null) {
            throw new UserDomainException("role", "role cannot be null");
        }

        this.role = role;
    }

    public void setEmail(String email) {
        if  (email == null || email.isBlank()) {
            throw new UserDomainException("email", "email cannot be null or blank");
        }

        if  (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new UserDomainException("email", "invalid email");
        }

        this.email = email;
    }

}
