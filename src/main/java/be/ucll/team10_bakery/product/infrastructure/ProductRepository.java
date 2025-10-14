package be.ucll.team10_bakery.product.infrastructure;

import be.ucll.team10_bakery.product.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
