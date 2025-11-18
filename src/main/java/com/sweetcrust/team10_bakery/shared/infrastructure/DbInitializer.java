package com.sweetcrust.team10_bakery.shared.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.inventory.domain.entities.InventoryItem;
import com.sweetcrust.team10_bakery.inventory.infrastructure.InventoryItemRepository;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductCategoryRepository;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductVariantRepository;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
import com.sweetcrust.team10_bakery.shop.domain.valueobjects.CountryCode;
import com.sweetcrust.team10_bakery.user.domain.entities.User;
import com.sweetcrust.team10_bakery.user.domain.valueobjects.UserRole;
import com.sweetcrust.team10_bakery.user.infrastructure.UserRepository;
import com.sweetcrust.team10_bakery.shop.infrastructure.ShopRepository;
import org.springframework.stereotype.Component;

import com.sweetcrust.team10_bakery.product.domain.entities.Product;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductCategory;
import com.sweetcrust.team10_bakery.product.domain.entities.ProductVariant;
import com.sweetcrust.team10_bakery.product.domain.valueobjects.ProductSize;
import com.sweetcrust.team10_bakery.product.infrastructure.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {
        private final ProductRepository productRepository;
        private final ProductVariantRepository productVariantRepository;
        private final ProductCategoryRepository categoryRepository;
        private final OrderRepository orderRepository;
        private final UserRepository userRepository;
        private final ShopRepository shopRepository;
        private final CartRepository cartRepository;
        private final InventoryItemRepository inventoryItemRepository;

        public DbInitializer(ProductRepository productRepository, OrderRepository orderRepository,
                        UserRepository userRepository, ShopRepository shopRepository, CartRepository cartRepository,
                        ProductVariantRepository productVariantRepository,
                        ProductCategoryRepository categoryRepository, InventoryItemRepository inventoryItemRepository) {
                this.productRepository = productRepository;
                this.productVariantRepository = productVariantRepository;
                this.categoryRepository = categoryRepository;
                this.orderRepository = orderRepository;
                this.userRepository = userRepository;
                this.shopRepository = shopRepository;
                this.cartRepository = cartRepository;
                this.inventoryItemRepository = inventoryItemRepository;
        }

        private void clearAll() {
                orderRepository.deleteAll();
                productRepository.deleteAll();
                productVariantRepository.deleteAll();
                categoryRepository.deleteAll();
                userRepository.deleteAll();
                shopRepository.deleteAll();
                cartRepository.deleteAll();
                inventoryItemRepository.deleteAll();
        }

        @PostConstruct
        public void init() {
                clearAll();

                ProductCategory cakes = new ProductCategory("Cakes", "A slice of heaven in every bite");
                ProductCategory pastries = new ProductCategory("Pastries", "Flaky, buttery happiness");
                ProductCategory donuts = new ProductCategory("Donuts", "Round perfection with a hole lotta love");
                ProductCategory cookies = new ProductCategory("Cookies", "Sweet little circles of joy");
                ProductCategory bread = new ProductCategory("Bread", "The daily loaf that keeps you going");
                ProductCategory cupcakes = new ProductCategory("Cupcakes", "Tiny treats with big flavor");
                ProductCategory pies = new ProductCategory("Pies", "Slice of life, literally");
                ProductCategory muffins = new ProductCategory("Muffins", "Breakfast champions in paper cups");

                categoryRepository.saveAll(List.of(cakes, pastries, donuts, cookies, bread, cupcakes, pies, muffins));

                Product chocolateLavaCake = new Product("Chocolate Lava Explosion",
                                "Molten chocolate center that erupts with every bite - volcanic levels of deliciousness",
                                BigDecimal.valueOf(6.99), true, cakes.getCategoryId());
                ProductVariant miniChocolateLavaCake = new ProductVariant(ProductSize.MINI, "Chocolate Lava Mini",
                                BigDecimal.valueOf(0.0), chocolateLavaCake.getProductId());
                miniChocolateLavaCake.setProduct(chocolateLavaCake);
                ProductVariant regularChocolateLavaCake = new ProductVariant(ProductSize.REGULAR,
                                "Chocolate Lava Regular", BigDecimal.valueOf(0.5), chocolateLavaCake.getProductId());
                regularChocolateLavaCake.setProduct(chocolateLavaCake);
                ProductVariant largeChocolateLavaCake = new ProductVariant(ProductSize.LARGE, "Chocolate Lava Large",
                                BigDecimal.valueOf(0.7), chocolateLavaCake.getProductId());
                largeChocolateLavaCake.setProduct(chocolateLavaCake);
                productRepository.save(chocolateLavaCake);
                productVariantRepository.save(miniChocolateLavaCake);
                productVariantRepository.save(regularChocolateLavaCake);
                productVariantRepository.save(largeChocolateLavaCake);

                Product redVelvetCake = new Product("Red Velvet Romance",
                                "Velvety smooth with cream cheese frosting - like a hug from grandma",
                                BigDecimal.valueOf(7.49), true, cakes.getCategoryId());
                ProductVariant miniRedVelvetCake = new ProductVariant(ProductSize.MINI, "Red Velvet Mini",
                                BigDecimal.valueOf(0.0), redVelvetCake.getProductId());
                miniRedVelvetCake.setProduct(redVelvetCake);
                ProductVariant regularRedVelvetCake = new ProductVariant(ProductSize.REGULAR, "Red Velvet Regular",
                                BigDecimal.valueOf(0.5), redVelvetCake.getProductId());
                regularRedVelvetCake.setProduct(redVelvetCake);
                ProductVariant largeRedVelvetCake = new ProductVariant(ProductSize.LARGE, "Red Velvet Large",
                                BigDecimal.valueOf(0.7), redVelvetCake.getProductId());
                largeRedVelvetCake.setProduct(redVelvetCake);
                productRepository.save(redVelvetCake);
                productVariantRepository.save(miniRedVelvetCake);
                productVariantRepository.save(regularRedVelvetCake);
                productVariantRepository.save(largeRedVelvetCake);

                Product carrotCake = new Product("Carrot Cake Conspiracy",
                                "Vegetables never tasted this good - we won't tell if you won't",
                                BigDecimal.valueOf(5.99), true, cakes.getCategoryId());
                ProductVariant miniCarrotCake = new ProductVariant(ProductSize.MINI, "Carrot Cake Mini",
                                BigDecimal.valueOf(0.0), carrotCake.getProductId());
                miniCarrotCake.setProduct(carrotCake);
                ProductVariant regularCarrotCake = new ProductVariant(ProductSize.REGULAR, "Carrot Cake Regular",
                                BigDecimal.valueOf(0.5), carrotCake.getProductId());
                regularCarrotCake.setProduct(carrotCake);
                ProductVariant largeCarrotCake = new ProductVariant(ProductSize.LARGE, "Carrot Cake Large",
                                BigDecimal.valueOf(0.7), carrotCake.getProductId());
                largeCarrotCake.setProduct(carrotCake);
                productRepository.save(carrotCake);
                productVariantRepository.save(miniCarrotCake);
                productVariantRepository.save(regularCarrotCake);
                productVariantRepository.save(largeCarrotCake);

                Product tiramisu = new Product("Tiramisu Dream",
                                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                                BigDecimal.valueOf(8.49), true, cakes.getCategoryId());
                ProductVariant miniTiramisu = new ProductVariant(ProductSize.MINI, "Tiramisu Mini",
                                BigDecimal.valueOf(0.0), tiramisu.getProductId());
                miniTiramisu.setProduct(tiramisu);
                ProductVariant regularTiramisu = new ProductVariant(ProductSize.REGULAR, "Tiramisu Regular",
                                BigDecimal.valueOf(0.5), tiramisu.getProductId());
                regularTiramisu.setProduct(tiramisu);
                ProductVariant largeTiramisu = new ProductVariant(ProductSize.LARGE, "Tiramisu Large",
                                BigDecimal.valueOf(0.7), tiramisu.getProductId());
                largeTiramisu.setProduct(tiramisu);
                productRepository.save(tiramisu);
                productVariantRepository.save(miniTiramisu);
                productVariantRepository.save(regularTiramisu);
                productVariantRepository.save(largeTiramisu);

                Product croissant = new Product("Butter Croissant Supreme",
                                "1000 layers of buttery goodness - made by hand, destroyed in seconds",
                                BigDecimal.valueOf(3.49), true, pastries.getCategoryId());
                ProductVariant miniCroissant = new ProductVariant(ProductSize.MINI, "Croissant Mini",
                                BigDecimal.valueOf(0.0), croissant.getProductId());
                miniCroissant.setProduct(croissant);
                ProductVariant regularCroissant = new ProductVariant(ProductSize.REGULAR, "Croissant Regular",
                                BigDecimal.valueOf(0.5), croissant.getProductId());
                regularCroissant.setProduct(croissant);
                ProductVariant largeCroissant = new ProductVariant(ProductSize.LARGE, "Croissant Large",
                                BigDecimal.valueOf(0.7), croissant.getProductId());
                largeCroissant.setProduct(croissant);
                productRepository.save(croissant);
                productVariantRepository.save(miniCroissant);
                productVariantRepository.save(regularCroissant);
                productVariantRepository.save(largeCroissant);

                Product painAuChocolat = new Product("Pain au Chocolat Paradise",
                                "Chocolate wrapped in pastry perfection - the French know what's up",
                                BigDecimal.valueOf(3.99), true, pastries.getCategoryId());
                ProductVariant regularPainAuChocolat = new ProductVariant(ProductSize.REGULAR,
                                "Pain au Chocolat Regular", BigDecimal.valueOf(0.5), painAuChocolat.getProductId());
                regularPainAuChocolat.setProduct(painAuChocolat);
                productRepository.save(painAuChocolat);
                productVariantRepository.save(regularPainAuChocolat);

                Product danishPastry = new Product("Danish Delight",
                                "Cream cheese filling nestled in flaky layers - Copenhagen approved",
                                BigDecimal.valueOf(4.29), true, pastries.getCategoryId());
                ProductVariant regularDanish = new ProductVariant(ProductSize.REGULAR, "Danish Regular",
                                BigDecimal.valueOf(0.5), danishPastry.getProductId());
                regularDanish.setProduct(danishPastry);
                productRepository.save(danishPastry);
                productVariantRepository.save(regularDanish);

                Product appleTurnover = new Product("Apple Turnover Twist",
                                "Caramelized apples in a golden crust - autumn in every bite",
                                BigDecimal.valueOf(3.79), true, pastries.getCategoryId());
                ProductVariant regularAppleTurnover = new ProductVariant(ProductSize.REGULAR, "Apple Turnover Regular",
                                BigDecimal.valueOf(0.5), appleTurnover.getProductId());
                regularAppleTurnover.setProduct(appleTurnover);
                productRepository.save(appleTurnover);
                productVariantRepository.save(regularAppleTurnover);

                Product glazedDonut = new Product("Glazed Glory",
                                "Classic glazed perfection - shiny, sweet, and absolutely essential",
                                BigDecimal.valueOf(2.49), true, donuts.getCategoryId());
                ProductVariant regularGlazedDonut = new ProductVariant(ProductSize.REGULAR, "Glazed Donut Regular",
                                BigDecimal.valueOf(0.5), glazedDonut.getProductId());
                regularGlazedDonut.setProduct(glazedDonut);
                productRepository.save(glazedDonut);
                productVariantRepository.save(regularGlazedDonut);

                Product bostonCreamDonut = new Product("Boston Cream Dream",
                                "Cream-filled heaven with chocolate ganache - double the indulgence",
                                BigDecimal.valueOf(3.29), true, donuts.getCategoryId());
                ProductVariant regularBostonCreamDonut = new ProductVariant(ProductSize.REGULAR,
                                "Boston Cream Donut Regular", BigDecimal.valueOf(0.5), bostonCreamDonut.getProductId());
                regularBostonCreamDonut.setProduct(bostonCreamDonut);
                productRepository.save(bostonCreamDonut);
                productVariantRepository.save(regularBostonCreamDonut);

                Product sprinkleDonut = new Product("Rainbow Sprinkle Spectacular",
                                "Covered in happiness sprinkles - because life needs more color",
                                BigDecimal.valueOf(2.79), true, donuts.getCategoryId());
                ProductVariant regularSprinkleDonut = new ProductVariant(ProductSize.REGULAR, "Sprinkle Donut Regular",
                                BigDecimal.valueOf(0.5), sprinkleDonut.getProductId());
                regularSprinkleDonut.setProduct(sprinkleDonut);
                productRepository.save(sprinkleDonut);
                productVariantRepository.save(regularSprinkleDonut);

                Product mapleDonut = new Product("Maple Bacon Madness",
                                "Sweet maple glaze meets crispy bacon - the ultimate sweet-savory combo",
                                BigDecimal.valueOf(3.99), true, donuts.getCategoryId());
                ProductVariant regularMapleDonut = new ProductVariant(ProductSize.REGULAR, "Maple Donut Regular",
                                BigDecimal.valueOf(0.5), mapleDonut.getProductId());
                regularMapleDonut.setProduct(mapleDonut);
                productRepository.save(mapleDonut);
                productVariantRepository.save(regularMapleDonut);

                Product chocolateChipCookie = new Product("Chunky Chocolate Chip",
                                "Loaded with chocolate chunks - still warm from the oven vibes",
                                BigDecimal.valueOf(2.99), true, cookies.getCategoryId());
                ProductVariant regularChocolateChipCookie = new ProductVariant(ProductSize.REGULAR,
                                "Chocolate Chip Cookie Regular", BigDecimal.valueOf(0.5),
                                chocolateChipCookie.getProductId());
                regularChocolateChipCookie.setProduct(chocolateChipCookie);
                productRepository.save(chocolateChipCookie);
                productVariantRepository.save(regularChocolateChipCookie);

                Product macadamiaCookie = new Product("White Chocolate Macadamia Magic",
                                "Premium macadamia nuts and white chocolate - fancy cookie energy",
                                BigDecimal.valueOf(3.49), true, cookies.getCategoryId());
                ProductVariant regularMacadamiaCookie = new ProductVariant(ProductSize.REGULAR,
                                "Macadamia Cookie Regular", BigDecimal.valueOf(0.5), macadamiaCookie.getProductId());
                regularMacadamiaCookie.setProduct(macadamiaCookie);
                productRepository.save(macadamiaCookie);
                productVariantRepository.save(regularMacadamiaCookie);

                Product oatmealRaisinCookie = new Product("Oatmeal Raisin Redemption",
                                "Not chocolate chips but still amazing - trust us on this one",
                                BigDecimal.valueOf(2.79), true, cookies.getCategoryId());
                ProductVariant regularOatmealRaisinCookie = new ProductVariant(ProductSize.REGULAR,
                                "Oatmeal Cookie Regular", BigDecimal.valueOf(0.5), oatmealRaisinCookie.getProductId());
                regularOatmealRaisinCookie.setProduct(oatmealRaisinCookie);
                productRepository.save(oatmealRaisinCookie);
                productVariantRepository.save(regularOatmealRaisinCookie);

                Product sugarCookie = new Product("Sugar Cookie Celebration",
                                "Decorated with festive frosting - party in cookie form",
                                BigDecimal.valueOf(2.49), true, cookies.getCategoryId());
                ProductVariant miniSugarCookie = new ProductVariant(ProductSize.MINI, "Sugar Cookie Mini",
                                BigDecimal.valueOf(0.0), sugarCookie.getProductId());
                miniSugarCookie.setProduct(sugarCookie);
                productRepository.save(sugarCookie);
                productVariantRepository.save(miniSugarCookie);

                Product sourdoughBread = new Product("Sourdough Sensation",
                                "Tangy, crusty, and absolutely artisan - bread for bread snobs",
                                BigDecimal.valueOf(5.49), true, bread.getCategoryId());
                ProductVariant largeSourdoughBread = new ProductVariant(ProductSize.LARGE, "Sourdough Large",
                                BigDecimal.valueOf(0.7), sourdoughBread.getProductId());
                largeSourdoughBread.setProduct(sourdoughBread);
                productRepository.save(sourdoughBread);
                productVariantRepository.save(largeSourdoughBread);

                Product baguette = new Product("French Baguette Authentique",
                                "Crusty outside, fluffy inside - ooh la la worthy",
                                BigDecimal.valueOf(4.29), true, bread.getCategoryId());
                ProductVariant largeBaguette = new ProductVariant(ProductSize.LARGE, "Baguette Large",
                                BigDecimal.valueOf(0.7), baguette.getProductId());
                largeBaguette.setProduct(baguette);
                productRepository.save(baguette);
                productVariantRepository.save(largeBaguette);

                Product cinnamonBread = new Product("Cinnamon Swirl Wonder",
                                "Ribbons of cinnamon sugar throughout - makes the best toast ever",
                                BigDecimal.valueOf(6.49), true, bread.getCategoryId());
                ProductVariant regularCinnamonBread = new ProductVariant(ProductSize.REGULAR, "Cinnamon Bread Regular",
                                BigDecimal.valueOf(0.5), cinnamonBread.getProductId());
                regularCinnamonBread.setProduct(cinnamonBread);
                productRepository.save(cinnamonBread);
                productVariantRepository.save(regularCinnamonBread);

                Product vanillaCupcake = new Product("Vanilla Bean Bliss",
                                "Classic vanilla with buttercream swirl - simple but never boring",
                                BigDecimal.valueOf(3.99), true, cupcakes.getCategoryId());
                ProductVariant regularVanillaCupcake = new ProductVariant(ProductSize.REGULAR,
                                "Vanilla Cupcake Regular", BigDecimal.valueOf(0.5), vanillaCupcake.getProductId());
                regularVanillaCupcake.setProduct(vanillaCupcake);
                productRepository.save(vanillaCupcake);
                productVariantRepository.save(regularVanillaCupcake);

                Product chocolateCupcake = new Product("Triple Chocolate Thunder",
                                "Chocolate cake, chocolate filling, chocolate frosting - chocoholics only",
                                BigDecimal.valueOf(4.29), true, cupcakes.getCategoryId());
                ProductVariant regularChocolateCupcake = new ProductVariant(ProductSize.REGULAR,
                                "Chocolate Cupcake Regular", BigDecimal.valueOf(0.5), chocolateCupcake.getProductId());
                regularChocolateCupcake.setProduct(chocolateCupcake);
                productRepository.save(chocolateCupcake);
                productVariantRepository.save(regularChocolateCupcake);

                Product lemonCupcake = new Product("Lemon Zest Zing",
                                "Tangy lemon cake with cream cheese frosting - sunshine in a wrapper",
                                BigDecimal.valueOf(3.79), true, cupcakes.getCategoryId());
                ProductVariant regularLemonCupcake = new ProductVariant(ProductSize.REGULAR, "Lemon Cupcake Regular",
                                BigDecimal.valueOf(0.5), lemonCupcake.getProductId());
                regularLemonCupcake.setProduct(lemonCupcake);
                productRepository.save(lemonCupcake);
                productVariantRepository.save(regularLemonCupcake);

                Product redVelvetCupcake = new Product("Red Velvet Mini Romance",
                                "All the velvet love in cupcake form - perfect for romantic gestures",
                                BigDecimal.valueOf(4.49), true, cupcakes.getCategoryId());
                ProductVariant miniRedVelvetCupcake = new ProductVariant(ProductSize.MINI, "Red Velvet Cupcake Mini",
                                BigDecimal.valueOf(0.0), redVelvetCupcake.getProductId());
                miniRedVelvetCupcake.setProduct(redVelvetCupcake);
                productRepository.save(redVelvetCupcake);
                productVariantRepository.save(miniRedVelvetCupcake);

                Product applePie = new Product("Classic Apple Pie",
                                "Grandma's recipe with a SweetCrust twist - as American as it gets",
                                BigDecimal.valueOf(12.99), true, pies.getCategoryId());
                ProductVariant largeApplePie = new ProductVariant(ProductSize.LARGE, "Apple Pie Large",
                                BigDecimal.valueOf(0.7), applePie.getProductId());
                largeApplePie.setProduct(applePie);
                productRepository.save(applePie);
                productVariantRepository.save(largeApplePie);

                Product pumpkinPie = new Product("Pumpkin Spice Paradise",
                                "Smooth pumpkin filling with whipped cream - fall in a slice",
                                BigDecimal.valueOf(11.99), true, pies.getCategoryId());
                ProductVariant largePumpkinPie = new ProductVariant(ProductSize.LARGE, "Pumpkin Pie Large",
                                BigDecimal.valueOf(0.7), pumpkinPie.getProductId());
                largePumpkinPie.setProduct(pumpkinPie);
                productRepository.save(pumpkinPie);
                productVariantRepository.save(largePumpkinPie);

                Product cherryPie = new Product("Cherry Bomb Pie",
                                "Tart cherries in a buttery crust - cherry on top not included",
                                BigDecimal.valueOf(13.49), true, pies.getCategoryId());
                ProductVariant largeCherryPie = new ProductVariant(ProductSize.LARGE, "Cherry Pie Large",
                                BigDecimal.valueOf(0.7), cherryPie.getProductId());
                largeCherryPie.setProduct(cherryPie);
                productRepository.save(cherryPie);
                productVariantRepository.save(largeCherryPie);

                Product blueberryMuffin = new Product("Blueberry Burst Muffin",
                                "Bursting with fresh blueberries - practically a health food",
                                BigDecimal.valueOf(3.49), true, muffins.getCategoryId());
                ProductVariant regularBlueberryMuffin = new ProductVariant(ProductSize.REGULAR,
                                "Blueberry Muffin Regular", BigDecimal.valueOf(0.5), blueberryMuffin.getProductId());
                regularBlueberryMuffin.setProduct(blueberryMuffin);
                productRepository.save(blueberryMuffin);
                productVariantRepository.save(regularBlueberryMuffin);

                Product bananaMuffin = new Product("Banana Nut Bonanza",
                                "Moist banana goodness with walnuts - the breakfast of champions",
                                BigDecimal.valueOf(3.29), true, muffins.getCategoryId());
                ProductVariant regularBananaMuffin = new ProductVariant(ProductSize.REGULAR, "Banana Muffin Regular",
                                BigDecimal.valueOf(0.5), bananaMuffin.getProductId());
                regularBananaMuffin.setProduct(bananaMuffin);
                productRepository.save(bananaMuffin);
                productVariantRepository.save(regularBananaMuffin);

                Product chocolateChipMuffin = new Product("Double Chocolate Chip Morning",
                                "Chocolate muffin with chocolate chips - yes you can have cake for breakfast",
                                BigDecimal.valueOf(3.79), true, muffins.getCategoryId());
                ProductVariant regularChocolateChipMuffin = new ProductVariant(ProductSize.REGULAR,
                                "Chocolate Chip Muffin Regular", BigDecimal.valueOf(0.5),
                                chocolateChipMuffin.getProductId());
                regularChocolateChipMuffin.setProduct(chocolateChipMuffin);
                productRepository.save(chocolateChipMuffin);
                productVariantRepository.save(regularChocolateChipMuffin);

                // Customers
                User donutDan = new User("DonutDan", "Glazed4Life!", "donut.dan@sweetcrust.com", UserRole.CUSTOMER);
                User muffinMary = new User("MuffinMary", "BlueberryBurst1!", "muffin.mary@sweetcrust.com",
                                UserRole.CUSTOMER);
                User croissantCarl = new User("CroissantCarl", "FlakeyGoodness7!", "croissant.carl@sweetcrust.com",
                                UserRole.CUSTOMER);
                User cupcakeClaire = new User("CupcakeClaire", "Sprinkles2024!", "cupcake.claire@sweetcrust.com",
                                UserRole.CUSTOMER);
                User piePatrick = new User("PiePatrick", "ApplePie4Ever!", "pie.patrick@sweetcrust.com",
                                UserRole.CUSTOMER);
                User cookieConnie = new User("CookieConnie", "ChocChip123!", "cookie.connie@sweetcrust.com",
                                UserRole.CUSTOMER);
                User breadBob = new User("BreadBob", "Sourdough99!", "bread.bob@sweetcrust.com", UserRole.CUSTOMER);
                User cakeCathy = new User("CakeCathy", "RedVelvet5!", "cake.cathy@sweetcrust.com", UserRole.CUSTOMER);
                User eclaireEllie = new User("EclaireEllie", "CreamPuff88!", "eclair.ellie@sweetcrust.com",
                                UserRole.CUSTOMER);
                User brownieBrenda = new User("BrownieBrenda", "Fudge4Days!", "brownie.brenda@sweetcrust.com",
                                UserRole.CUSTOMER);

                // Baker
                User bakerBill = new User("BakerBill", "KneadTheDough1!", "baker.bill@sweetcrust.com", UserRole.BAKER);
                User bakerBetty = new User("BakerBetty", "RiseAndShine2!", "baker.betty@sweetcrust.com",
                                UserRole.BAKER);
                User bakerBenny = new User("BakerBenny", "ProofingPro3!", "baker.benny@sweetcrust.com", UserRole.BAKER);
                User bakerBella = new User("BakerBella", "YeastMode4!", "baker.bella@sweetcrust.com", UserRole.BAKER);

                // Admin
                User adminAlice = new User("AdminAlice", "SuperAdmin99!", "admin.alice@sweetcrust.com", UserRole.ADMIN);
                User adminAlex = new User("AdminAlex", "BossMode2024!", "admin.alex@sweetcrust.com", UserRole.ADMIN);

                userRepository.save(donutDan);
                userRepository.save(muffinMary);
                userRepository.save(croissantCarl);
                userRepository.save(cupcakeClaire);
                userRepository.save(piePatrick);
                userRepository.save(cookieConnie);
                userRepository.save(breadBob);
                userRepository.save(cakeCathy);
                userRepository.save(eclaireEllie);
                userRepository.save(brownieBrenda);
                userRepository.save(bakerBill);
                userRepository.save(bakerBetty);
                userRepository.save(bakerBenny);
                userRepository.save(bakerBella);
                userRepository.save(adminAlice);
                userRepository.save(adminAlex);

                // Shops
                Shop parisShop = new Shop("SweetCrust Paris",
                                Address.builder()
                                                .setStreet("Rue de la Baguette 42")
                                                .setCity("Paris")
                                                .setPostalCode("75001")
                                                .setCountry("France")
                                                .build(),
                                "bonjour@sweetcrust.com",
                                new CountryCode("FR"));

                Shop tokyoShop = new Shop("SweetCrust Tokyo",
                                Address.builder()
                                                .setStreet("Shibuya Crossing 1")
                                                .setCity("Tokyo")
                                                .setPostalCode("1500001")
                                                .setCountry("Japan")
                                                .build(),
                                "konnichiwa@sweetcrust.com",
                                new CountryCode("JP"));

                Shop newYorkShop = new Shop("SweetCrust New York",
                                Address.builder()
                                                .setStreet("Broadway 123")
                                                .setCity("New York")
                                                .setPostalCode("10001")
                                                .setCountry("United States")
                                                .build(),
                                "bigapple@sweetcrust.com",
                                new CountryCode("US"));

                Shop londonShop = new Shop("SweetCrust London",
                                Address.builder()
                                                .setStreet("Baker Street 221B")
                                                .setCity("London")
                                                .setPostalCode("NW16XE")
                                                .setCountry("United Kingdom")
                                                .build(),
                                "cheerio@sweetcrust.com",
                                new CountryCode("GB"));

                Shop berlinShop = new Shop("SweetCrust Berlin",
                                Address.builder()
                                                .setStreet("Alexanderplatz 5")
                                                .setCity("Berlin")
                                                .setPostalCode("10178")
                                                .setCountry("Germany")
                                                .build(),
                                "gutentag@sweetcrust.com",
                                new CountryCode("DE"));

                Shop romeShop = new Shop("SweetCrust Rome",
                                Address.builder()
                                                .setStreet("Via del Corso 99")
                                                .setCity("Rome")
                                                .setPostalCode("00186")
                                                .setCountry("Italy")
                                                .build(),
                                "ciao@sweetcrust.com",
                                new CountryCode("IT"));

                Shop barcelonaShop = new Shop("SweetCrust Barcelona",
                                Address.builder()
                                                .setStreet("La Rambla 88")
                                                .setCity("Barcelona")
                                                .setPostalCode("08002")
                                                .setCountry("Spain")
                                                .build(),
                                "hola@sweetcrust.com",
                                new CountryCode("ES"));

                Shop amsterdamShop = new Shop("SweetCrust Amsterdam",
                                Address.builder()
                                                .setStreet("Damrak 12")
                                                .setCity("Amsterdam")
                                                .setPostalCode("1012LG")
                                                .setCountry("Netherlands")
                                                .build(),
                                "hallo@sweetcrust.com",
                                new CountryCode("NL"));

                shopRepository.save(parisShop);
                shopRepository.save(tokyoShop);
                shopRepository.save(newYorkShop);
                shopRepository.save(londonShop);
                shopRepository.save(berlinShop);
                shopRepository.save(romeShop);
                shopRepository.save(barcelonaShop);
                shopRepository.save(amsterdamShop);

                InventoryItem parisInventory = new InventoryItem(parisShop.getShopId(), largeApplePie.getVariantId(),
                                10);
                InventoryItem tokyoInventory = new InventoryItem(tokyoShop.getShopId(), miniCroissant.getVariantId(),
                                50);
                InventoryItem newYorkInventory = new InventoryItem(newYorkShop.getShopId(),
                                regularChocolateCupcake.getVariantId(), 0);
                InventoryItem londonInventory = new InventoryItem(londonShop.getShopId(),
                                regularGlazedDonut.getVariantId(), 40);
                InventoryItem berlinInventory = new InventoryItem(berlinShop.getShopId(),
                                regularChocolateChipCookie.getVariantId(), 20);
                InventoryItem romeInventory = new InventoryItem(romeShop.getShopId(), largeTiramisu.getVariantId(), 15);
                InventoryItem barcelonaInventory = new InventoryItem(barcelonaShop.getShopId(),
                                regularBlueberryMuffin.getVariantId(), 25);
                InventoryItem amsterdamInventory = new InventoryItem(amsterdamShop.getShopId(),
                                largePumpkinPie.getVariantId(), 10);

                inventoryItemRepository.save(parisInventory);
                inventoryItemRepository.save(tokyoInventory);
                inventoryItemRepository.save(newYorkInventory);
                inventoryItemRepository.save(londonInventory);
                inventoryItemRepository.save(berlinInventory);
                inventoryItemRepository.save(romeInventory);
                inventoryItemRepository.save(barcelonaInventory);
                inventoryItemRepository.save(amsterdamInventory);

                // B2C
                Cart donutCart = new Cart();
                donutCart.addCartItem(CartItem.fromVariant(regularGlazedDonut, 2));
                donutCart.addCartItem(CartItem.fromVariant(regularBostonCreamDonut, 5));
                donutCart.addCartItem(CartItem.fromVariant(regularMapleDonut, 3));
                donutCart.setOwnerId(donutDan.getUserId());

                Cart cupcakeCart = new Cart();
                cupcakeCart.addCartItem(CartItem.fromVariant(regularChocolateCupcake, 10));
                cupcakeCart.addCartItem(CartItem.fromVariant(regularLemonCupcake, 5));
                cupcakeCart.setOwnerId(cupcakeClaire.getUserId());

                Cart pieCart = new Cart();
                pieCart.addCartItem(CartItem.fromVariant(largeApplePie, 1));
                pieCart.addCartItem(CartItem.fromVariant(largeApplePie, 2));
                pieCart.setOwnerId(piePatrick.getUserId());

                Cart cookieCart = new Cart();
                cookieCart.addCartItem(CartItem.fromVariant(regularChocolateChipCookie, 20));
                cookieCart.addCartItem(CartItem.fromVariant(regularMacadamiaCookie, 5));
                cookieCart.setOwnerId(cookieConnie.getUserId());

                Cart cakeCart = new Cart();
                cakeCart.addCartItem(CartItem.fromVariant(largeRedVelvetCake, 2));
                cakeCart.addCartItem(CartItem.fromVariant(largeChocolateLavaCake, 5));
                cakeCart.setOwnerId(cakeCathy.getUserId());

                Cart breadCart = new Cart();
                breadCart.addCartItem(CartItem.fromVariant(largeSourdoughBread, 2));
                breadCart.addCartItem(CartItem.fromVariant(largeBaguette, 5));
                breadCart.setOwnerId(breadBob.getUserId());

                Cart croissantCart = new Cart();
                croissantCart.addCartItem(CartItem.fromVariant(largeCroissant, 7));
                croissantCart.addCartItem(CartItem.fromVariant(largeTiramisu, 10));
                croissantCart.setOwnerId(croissantCarl.getUserId());

                Cart muffinCart = new Cart();
                muffinCart.addCartItem(CartItem.fromVariant(regularBananaMuffin, 4));
                muffinCart.addCartItem(CartItem.fromVariant(regularBlueberryMuffin, 10));
                muffinCart.addCartItem(CartItem.fromVariant(regularChocolateChipMuffin, 3));
                muffinCart.setOwnerId(muffinMary.getUserId());

                cartRepository.save(donutCart);
                cartRepository.save(cupcakeCart);
                cartRepository.save(pieCart);
                cartRepository.save(cookieCart);
                cartRepository.save(cakeCart);
                cartRepository.save(breadCart);
                cartRepository.save(croissantCart);
                cartRepository.save(muffinCart);

                Order cupcakeOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Cupcake Lane 8")
                                                .setCity("Frostington")
                                                .setPostalCode("23456")
                                                .setCountry("Sweetland")
                                                .build(),
                                LocalDateTime.now().plusDays(2),
                                cupcakeClaire.getUserId(),
                                cupcakeCart.getCartId());

                Order pieOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Pie Place 99")
                                                .setCity("Crustville")
                                                .setPostalCode("34567")
                                                .setCountry("Pieland")
                                                .build(),
                                LocalDateTime.now().plusDays(3),
                                piePatrick.getUserId(),
                                pieCart.getCartId());

                Order cookieOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Cookie Court 7")
                                                .setCity("Chocochip City")
                                                .setPostalCode("45678")
                                                .setCountry("Cookieland")
                                                .build(),
                                LocalDateTime.now().plusDays(1),
                                cookieConnie.getUserId(),
                                cookieCart.getCartId());

                Order cakeOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Cake Circle 3")
                                                .setCity("Velvetville")
                                                .setPostalCode("56789")
                                                .setCountry("Cakeland")
                                                .build(),
                                LocalDateTime.now().plusDays(4),
                                cakeCathy.getUserId(),
                                cakeCart.getCartId());

                Order breadOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Bread Boulevard 21")
                                                .setCity("Sourdough Springs")
                                                .setPostalCode("67890")
                                                .setCountry("Breadland")
                                                .build(),
                                LocalDateTime.now().plusDays(1),
                                breadBob.getUserId(),
                                breadCart.getCartId());

                Order croissantOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Flaky Street 18")
                                                .setCity("Buttertown")
                                                .setPostalCode("78901")
                                                .setCountry("Pastryville")
                                                .build(),
                                LocalDateTime.now().plusDays(2),
                                croissantCarl.getUserId(),
                                croissantCart.getCartId());

                Order donutOrder = Order.createB2C(
                                Address.builder().setStreet("Donut Drive 15").setCity("Glazeville")
                                                .setPostalCode("12345").setCountry("Donutland").build(),
                                LocalDateTime.now().plusDays(1),
                                donutDan.getUserId(),
                                donutCart.getCartId());

                Order muffinOrder = Order.createB2C(
                                Address.builder()
                                                .setStreet("Muffin Manor 44")
                                                .setCity("Blueberry Hills")
                                                .setPostalCode("89012")
                                                .setCountry("Muffinland")
                                                .build(),
                                LocalDateTime.now().plusDays(1),
                                muffinMary.getUserId(),
                                muffinCart.getCartId());

                orderRepository.save(cupcakeOrder);
                orderRepository.save(pieOrder);
                orderRepository.save(cookieOrder);
                orderRepository.save(cakeOrder);
                orderRepository.save(breadOrder);
                orderRepository.save(croissantOrder);
                orderRepository.save(donutOrder);
                orderRepository.save(muffinOrder);

                // B2B
                Cart parisToLondonCart = new Cart();
                parisToLondonCart.addCartItem(CartItem.fromVariant(regularCarrotCake, 10));
                parisToLondonCart.addCartItem(CartItem.fromVariant(miniCroissant, 50));
                parisToLondonCart.addCartItem(CartItem.fromVariant(regularCinnamonBread, 15));
                parisToLondonCart.setOwnerId(bakerBenny.getUserId());

                Cart tokyoToNewYorkCart = new Cart();
                tokyoToNewYorkCart.addCartItem(CartItem.fromVariant(miniCroissant, 50));
                tokyoToNewYorkCart.addCartItem(CartItem.fromVariant(miniSugarCookie, 15));
                tokyoToNewYorkCart.setOwnerId(bakerBenny.getUserId());

                Cart romeToBarcelonaCart = new Cart();
                romeToBarcelonaCart.addCartItem(CartItem.fromVariant(miniCroissant, 50));
                romeToBarcelonaCart.addCartItem(CartItem.fromVariant(largeTiramisu, 100));
                romeToBarcelonaCart.setOwnerId(bakerBella.getUserId());

                Cart berlinToAmsterdamCart = new Cart();
                berlinToAmsterdamCart.addCartItem(CartItem.fromVariant(miniRedVelvetCake, 10));
                berlinToAmsterdamCart.addCartItem(CartItem.fromVariant(largePumpkinPie, 20));
                berlinToAmsterdamCart.setOwnerId(bakerBill.getUserId());

                cartRepository.save(parisToLondonCart);
                cartRepository.save(tokyoToNewYorkCart);
                cartRepository.save(romeToBarcelonaCart);
                cartRepository.save(berlinToAmsterdamCart);

                Order parisToLondon = Order.createB2B(
                                LocalDateTime.now().plusDays(5),
                                londonShop.getShopId(),
                                parisShop.getShopId(),
                                parisToLondonCart.getCartId());

                Order tokyoToNewYork = Order.createB2B(
                                LocalDateTime.now().plusDays(7),
                                newYorkShop.getShopId(),
                                tokyoShop.getShopId(),
                                tokyoToNewYorkCart.getCartId());

                Order romeToBarcelona = Order.createB2B(
                                LocalDateTime.now().plusDays(4),
                                barcelonaShop.getShopId(),
                                romeShop.getShopId(),
                                romeToBarcelonaCart.getCartId());

                Order berlinToAmsterdam = Order.createB2B(
                                LocalDateTime.now().plusDays(3),
                                amsterdamShop.getShopId(),
                                berlinShop.getShopId(),
                                berlinToAmsterdamCart.getCartId());

                orderRepository.save(parisToLondon);
                orderRepository.save(tokyoToNewYork);
                orderRepository.save(romeToBarcelona);
                orderRepository.save(berlinToAmsterdam);
        }
}