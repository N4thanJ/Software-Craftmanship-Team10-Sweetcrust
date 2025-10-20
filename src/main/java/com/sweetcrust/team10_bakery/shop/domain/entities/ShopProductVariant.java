package com.sweetcrust.team10_bakery.shop.domain.entities;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.shop.domain.ShopDomainException;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopProductVariantId;
import jakarta.persistence.*;

@Entity
@Table(name = "shop_product_variants")
public class ShopProductVariant {

    @EmbeddedId
    private ShopProductVariantId shopProductVariantId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "shop_id"))
    private ShopId shopId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "variant_id"))
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
