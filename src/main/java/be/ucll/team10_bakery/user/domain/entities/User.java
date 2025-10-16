package be.ucll.team10_bakery.user.domain.entities;

import java.util.UUID;

import be.ucll.team10_bakery.user.domain.valueobjects.UserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @EmbeddedId
    private UserId id;

    @NotBlank(message = "Username should not be blank")
    private String username;

    @NotBlank(message = "Password should not be blank")
    private String password;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public UserId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
