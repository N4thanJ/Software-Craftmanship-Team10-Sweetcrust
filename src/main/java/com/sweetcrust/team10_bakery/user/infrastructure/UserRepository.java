package com.sweetcrust.team10_bakery.user.infrastructure;

import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
