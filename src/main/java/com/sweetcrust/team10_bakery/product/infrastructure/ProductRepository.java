package com.sweetcrust.team10_bakery.product.infrastructure;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, ProductId> {
    boolean existsByName(String name);
}
