package be.ucll.team10_bakery.user.infrastructure;

import be.ucll.team10_bakery.user.domain.entities.User;
import be.ucll.team10_bakery.user.domain.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByEmail(String email);
}
