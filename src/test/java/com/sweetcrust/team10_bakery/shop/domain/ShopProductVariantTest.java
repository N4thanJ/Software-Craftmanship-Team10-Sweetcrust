package com.sweetcrust.team10_bakery.shop.domain;

import com.sweetcrust.team10_bakery.product.domain.valueobjects.VariantId;
import com.sweetcrust.team10_bakery.shop.domain.entities.ShopProductVariant;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopId;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.ShopProductVariantId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopProductVariantTest {

    @Test
    void givenValidShopIdAndVariantId_whenCreatingShopProductVariant_thenVariantIsCreated() {
        // given
        ShopId shopId = new ShopId();
        VariantId variantId = new VariantId();

        // when
        ShopProductVariant shopProductVariant = new ShopProductVariant(shopId, variantId);

        // then
        assertNotNull(shopProductVariant);
        assertNotNull(shopProductVariant.getShopProductVariantId());
        assertEquals(shopId, shopProductVariant.getShopId());
        assertEquals(variantId, shopProductVariant.getVariantId());
    }

    @Test
    void givenNullShopId_whenCreatingShopProductVariant_thenThrowsException() {
        // given
        VariantId variantId = new VariantId();

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new ShopProductVariant(null, variantId));

        // then
        assertEquals("shopId", exception.getField());
        assertEquals("shopId should not be null", exception.getMessage());
    }

    @Test
    void givenNullVariantId_whenCreatingShopProductVariant_thenThrowsException() {
        // given
        ShopId shopId = new ShopId();

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> new ShopProductVariant(shopId, null));

        // then
        assertEquals("variantId", exception.getField());
        assertEquals("variantId should not be null", exception.getMessage());
    }

    @Test
    void givenValidShopProductVariant_whenGettingIds_thenTheyMatch() {
        // given
        ShopId shopId = new ShopId();
        VariantId variantId = new VariantId();

        // when
        ShopProductVariant variant = new ShopProductVariant(shopId, variantId);

        // then
        assertEquals(shopId, variant.getShopId());
        assertEquals(variantId, variant.getVariantId());
        assertNotNull(variant.getShopProductVariantId());
    }

    @Test
    void givenExistingVariant_whenChangingShopIdToNull_thenThrowsException() {
        // given
        ShopProductVariant variant = new ShopProductVariant(new ShopId(), new VariantId());

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> variant.setShopId(null));

        // then
        assertEquals("shopId", exception.getField());
        assertEquals("shopId should not be null", exception.getMessage());
    }

    @Test
    void givenExistingVariant_whenChangingVariantIdToNull_thenThrowsException() {
        // given
        ShopProductVariant variant = new ShopProductVariant(new ShopId(), new VariantId());

        // when
        ShopDomainException exception = assertThrows(ShopDomainException.class,
                () -> variant.setVariantId(null));

        // then
        assertEquals("variantId", exception.getField());
        assertEquals("variantId should not be null", exception.getMessage());
    }

    @Test
    void givenExistingVariant_whenSettingValidNewIds_thenUpdatesSuccessfully() {
        // given
        ShopProductVariant variant = new ShopProductVariant(new ShopId(), new VariantId());
        ShopId newShopId = new ShopId();
        VariantId newVariantId = new VariantId();

        // when
        variant.setShopId(newShopId);
        variant.setVariantId(newVariantId);

        // then
        assertEquals(newShopId, variant.getShopId());
        assertEquals(newVariantId, variant.getVariantId());
    }

    @Test
    void givenNewVariant_whenCheckingEmbeddedId_thenItExists() {
        // given
        ShopProductVariant variant = new ShopProductVariant(new ShopId(), new VariantId());

        // when
        ShopProductVariantId id = variant.getShopProductVariantId();

        // then
        assertNotNull(id, "ShopProductVariantId should be automatically generated");
    }
}
