package com.sweetcrust.team10_bakery.cart.infrastructure;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.valueobjects.CartId;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
  Optional<Cart> findByOwnerId(UserId ownerId);
}
