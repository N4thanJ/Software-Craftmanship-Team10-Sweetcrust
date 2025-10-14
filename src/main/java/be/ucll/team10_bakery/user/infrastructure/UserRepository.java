package be.ucll.team10_bakery.user.infrastructure;

import be.ucll.team10_bakery.user.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
