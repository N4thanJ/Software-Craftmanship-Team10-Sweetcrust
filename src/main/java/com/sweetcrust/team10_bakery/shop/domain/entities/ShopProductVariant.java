package com.sweetcrust.team10_bakery.shop.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.shop.domain.ShopDomainException;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopProductVariantId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "shop_product_variants")
public class ShopProductVariant {

    @EmbeddedId
    private ShopProductVariantId shopProductVariantId;

    @Embedded
    private ShopId shopId;

    @Embedded
    private VariantId variantId;

    protected ShopProductVariant() {
    }

    public ShopProductVariant(ShopId shopId, VariantId variantId) {
        this.shopProductVariantId = new ShopProductVariantId();
        setShopId(shopId);
        setVariantId(variantId);
    }

    public ShopProductVariantId getShopProductVariantId() {
        return shopProductVariantId;
    }

    public ShopId getShopId() {
        return shopId;
    }

    public VariantId getVariantId() {
        return variantId;
    }

    public void setShopId(ShopId shopId) {
        if (shopId == null) {
            throw new ShopDomainException("shopId", "shopId should not be null");
        }
        this.shopId = shopId;
    }

    public void setVariantId(VariantId variantId) {
        if (variantId == null) {
            throw new ShopDomainException("variantId", "variantId should not be null");
        }
        this.variantId = variantId;
    }
}
