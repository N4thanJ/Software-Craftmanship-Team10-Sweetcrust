package be.ucll.team10_bakery.product.infrastructure;

import be.ucll.team10_bakery.product.domain.entities.Product;
import be.ucll.team10_bakery.product.domain.valueobjects.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, ProductId> {
}
