package com.sweetcrust.team10_bakery.product.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.ProductDomainException;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.CategoryId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_categories")
public class ProductCategory {

  @EmbeddedId private CategoryId categoryId;

  private String name;

  private String description;

  protected ProductCategory() {}

  public ProductCategory(String name, String description) {
    this.categoryId = new CategoryId();
    setName(name);
    setDescription(description);
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new ProductDomainException("category", "name should not be null or blank");
    }
    this.name = name;
  }

  public void setDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new ProductDomainException("category", "description should not be null or blank");
    }
    this.description = description;
  }
}
