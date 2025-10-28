package com.sweetcrust.team10_bakery.shared.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sweetcrust.team10_bakery.cart.domain.entities.Cart;
import com.sweetcrust.team10_bakery.cart.domain.entities.CartItem;
import com.sweetcrust.team10_bakery.cart.infrastructure.CartRepository;
import com.sweetcrust.team10_bakery.order.domain.entities.Order;
import com.sweetcrust.team10_bakery.order.infrastructure.OrderRepository;
import com.sweetcrust.team10_bakery.shared.domain.valueobjects.Address;
import com.sweetcrust.team10_bakery.shop.domain.entities.Shop;
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
        private final OrderRepository orderRepository;
        private final UserRepository userRepository;
        private final ShopRepository shopRepository;
        private final CartRepository cartRepository;

        public DbInitializer(ProductRepository productRepository, OrderRepository orderRepository,
                        UserRepository userRepository, ShopRepository shopRepository, CartRepository cartRepository) {
                this.productRepository = productRepository;
                this.orderRepository = orderRepository;
                this.userRepository = userRepository;
                this.shopRepository = shopRepository;
                this.cartRepository = cartRepository;
        }

        private void clearAll() {
                orderRepository.deleteAll();
                productRepository.deleteAll();
                userRepository.deleteAll();
                shopRepository.deleteAll();
                cartRepository.deleteAll();
        }

        @PostConstruct
        public void init() {
                clearAll();

                // Categories
                ProductCategory cakes = new ProductCategory("Cakes", "A slice of heaven in every bite");
                ProductCategory pastries = new ProductCategory("Pastries", "Flaky, buttery happiness");
                ProductCategory donuts = new ProductCategory("Donuts", "Round perfection with a hole lotta love");
                ProductCategory cookies = new ProductCategory("Cookies", "Sweet little circles of joy");
                ProductCategory bread = new ProductCategory("Bread", "The daily loaf that keeps you going");
                ProductCategory cupcakes = new ProductCategory("Cupcakes", "Tiny treats with big flavor");
                ProductCategory pies = new ProductCategory("Pies", "Slice of life, literally");
                ProductCategory muffins = new ProductCategory("Muffins", "Breakfast champions in paper cups");

                // Varianten
                ProductVariant miniVariant = new ProductVariant(ProductSize.MINI, "Bite-Sized",
                                BigDecimal.valueOf(0.3));
                ProductVariant regularVariant = new ProductVariant(ProductSize.REGULAR, "Classic Size",
                                BigDecimal.valueOf(0.5));
                ProductVariant largeVariant = new ProductVariant(ProductSize.LARGE, "Sharing Size",
                                BigDecimal.valueOf(0.7));

                // Producten
                Product chocolateLavaCake = new Product(
                                "Chocolate Lava Explosion",
                                "Molten chocolate center that erupts with every bite - volcanic levels of deliciousness",
                                BigDecimal.valueOf(6.99),
                                true,
                                regularVariant.getVariantId(),
                                cakes.getCategoryId());

                Product redVelvetCake = new Product(
                                "Red Velvet Romance",
                                "Velvety smooth with cream cheese frosting - like a hug from grandma",
                                BigDecimal.valueOf(7.49),
                                true,
                                largeVariant.getVariantId(),
                                cakes.getCategoryId());

                Product carrotCake = new Product(
                                "Carrot Cake Conspiracy",
                                "Vegetables never tasted this good - we won't tell if you won't",
                                BigDecimal.valueOf(5.99),
                                true,
                                regularVariant.getVariantId(),
                                cakes.getCategoryId());

                Product tiramisu = new Product(
                                "Tiramisu Dream",
                                "Italian coffee-soaked layers of pure bliss - pick-me-up in dessert form",
                                BigDecimal.valueOf(8.49),
                                true,
                                regularVariant.getVariantId(),
                                cakes.getCategoryId());

                Product croissant = new Product(
                                "Butter Croissant Supreme",
                                "1000 layers of buttery goodness - made by hand, destroyed in seconds",
                                BigDecimal.valueOf(3.49),
                                true,
                                regularVariant.getVariantId(),
                                pastries.getCategoryId());

                Product painAuChocolat = new Product(
                                "Pain au Chocolat Paradise",
                                "Chocolate wrapped in pastry perfection - the French know what's up",
                                BigDecimal.valueOf(3.99),
                                true,
                                regularVariant.getVariantId(),
                                pastries.getCategoryId());

                Product danishPastry = new Product(
                                "Danish Delight",
                                "Cream cheese filling nestled in flaky layers - Copenhagen approved",
                                BigDecimal.valueOf(4.29),
                                true,
                                regularVariant.getVariantId(),
                                pastries.getCategoryId());

                Product appleTurnover = new Product(
                                "Apple Turnover Twist",
                                "Caramelized apples in a golden crust - autumn in every bite",
                                BigDecimal.valueOf(3.79),
                                true,
                                regularVariant.getVariantId(),
                                pastries.getCategoryId());

                Product glazedDonut = new Product(
                                "Glazed Glory",
                                "Classic glazed perfection - shiny, sweet, and absolutely essential",
                                BigDecimal.valueOf(2.49),
                                true,
                                regularVariant.getVariantId(),
                                donuts.getCategoryId());

                Product bostonCreamDonut = new Product(
                                "Boston Cream Dream",
                                "Cream-filled heaven with chocolate ganache - double the indulgence",
                                BigDecimal.valueOf(3.29),
                                true,
                                regularVariant.getVariantId(),
                                donuts.getCategoryId());

                Product sprinkleDonut = new Product(
                                "Rainbow Sprinkle Spectacular",
                                "Covered in happiness sprinkles - because life needs more color",
                                BigDecimal.valueOf(2.79),
                                true,
                                regularVariant.getVariantId(),
                                donuts.getCategoryId());

                Product mapleDonut = new Product(
                                "Maple Bacon Madness",
                                "Sweet maple glaze meets crispy bacon - the ultimate sweet-savory combo",
                                BigDecimal.valueOf(3.99),
                                true,
                                regularVariant.getVariantId(),
                                donuts.getCategoryId());

                Product chocolateChipCookie = new Product(
                                "Chunky Chocolate Chip",
                                "Loaded with chocolate chunks - still warm from the oven vibes",
                                BigDecimal.valueOf(2.99),
                                true,
                                regularVariant.getVariantId(),
                                cookies.getCategoryId());

                Product macadamiaCookie = new Product(
                                "White Chocolate Macadamia Magic",
                                "Premium macadamia nuts and white chocolate - fancy cookie energy",
                                BigDecimal.valueOf(3.49),
                                true,
                                regularVariant.getVariantId(),
                                cookies.getCategoryId());

                Product oatmealRaisinCookie = new Product(
                                "Oatmeal Raisin Redemption",
                                "Not chocolate chips but still amazing - trust us on this one",
                                BigDecimal.valueOf(2.79),
                                true,
                                regularVariant.getVariantId(),
                                cookies.getCategoryId());

                Product sugarCookie = new Product(
                                "Sugar Cookie Celebration",
                                "Decorated with festive frosting - party in cookie form",
                                BigDecimal.valueOf(2.49),
                                true,
                                miniVariant.getVariantId(),
                                cookies.getCategoryId());

                Product sourdoughBread = new Product(
                                "Sourdough Sensation",
                                "Tangy, crusty, and absolutely artisan - bread for bread snobs",
                                BigDecimal.valueOf(5.49),
                                true,
                                largeVariant.getVariantId(),
                                bread.getCategoryId());

                Product baguette = new Product(
                                "French Baguette Authentique",
                                "Crusty outside, fluffy inside - ooh la la worthy",
                                BigDecimal.valueOf(4.29),
                                true,
                                largeVariant.getVariantId(),
                                bread.getCategoryId());

                Product cinnamonBread = new Product(
                                "Cinnamon Swirl Wonder",
                                "Ribbons of cinnamon sugar throughout - makes the best toast ever",
                                BigDecimal.valueOf(6.49),
                                true,
                                regularVariant.getVariantId(),
                                bread.getCategoryId());

                Product vanillaCupcake = new Product(
                                "Vanilla Bean Bliss",
                                "Classic vanilla with buttercream swirl - simple but never boring",
                                BigDecimal.valueOf(3.99),
                                true,
                                regularVariant.getVariantId(),
                                cupcakes.getCategoryId());

                Product chocolateCupcake = new Product(
                                "Triple Chocolate Thunder",
                                "Chocolate cake, chocolate filling, chocolate frosting - chocoholics only",
                                BigDecimal.valueOf(4.29),
                                true,
                                regularVariant.getVariantId(),
                                cupcakes.getCategoryId());

                Product lemonCupcake = new Product(
                                "Lemon Zest Zing",
                                "Tangy lemon cake with cream cheese frosting - sunshine in a wrapper",
                                BigDecimal.valueOf(3.79),
                                true,
                                regularVariant.getVariantId(),
                                cupcakes.getCategoryId());

                Product redVelvetCupcake = new Product(
                                "Red Velvet Mini Romance",
                                "All the velvet love in cupcake form - perfect for romantic gestures",
                                BigDecimal.valueOf(4.49),
                                true,
                                miniVariant.getVariantId(),
                                cupcakes.getCategoryId());

                Product applePie = new Product(
                                "Classic Apple Pie",
                                "Grandma's recipe with a SweetCrust twist - as American as it gets",
                                BigDecimal.valueOf(12.99),
                                true,
                                largeVariant.getVariantId(),
                                pies.getCategoryId());

                Product pumpkinPie = new Product(
                                "Pumpkin Spice Paradise",
                                "Smooth pumpkin filling with whipped cream - fall in a slice",
                                BigDecimal.valueOf(11.99),
                                true,
                                largeVariant.getVariantId(),
                                pies.getCategoryId());

                Product cherryPie = new Product(
                                "Cherry Bomb Pie",
                                "Tart cherries in a buttery crust - cherry on top not included",
                                BigDecimal.valueOf(13.49),
                                true,
                                largeVariant.getVariantId(),
                                pies.getCategoryId());

                Product blueberryMuffin = new Product(
                                "Blueberry Burst Muffin",
                                "Bursting with fresh blueberries - practically a health food",
                                BigDecimal.valueOf(3.49),
                                true,
                                regularVariant.getVariantId(),
                                muffins.getCategoryId());

                Product bananaMuffin = new Product(
                                "Banana Nut Bonanza",
                                "Moist banana goodness with walnuts - the breakfast of champions",
                                BigDecimal.valueOf(3.29),
                                true,
                                regularVariant.getVariantId(),
                                muffins.getCategoryId());

                Product chocolateChipMuffin = new Product(
                                "Double Chocolate Chip Morning",
                                "Chocolate muffin with chocolate chips - yes you can have cake for breakfast",
                                BigDecimal.valueOf(3.79),
                                true,
                                regularVariant.getVariantId(),
                                muffins.getCategoryId());

                productRepository.save(chocolateLavaCake);
                productRepository.save(redVelvetCake);
                productRepository.save(carrotCake);
                productRepository.save(tiramisu);
                productRepository.save(croissant);
                productRepository.save(painAuChocolat);
                productRepository.save(danishPastry);
                productRepository.save(appleTurnover);
                productRepository.save(glazedDonut);
                productRepository.save(bostonCreamDonut);
                productRepository.save(sprinkleDonut);
                productRepository.save(mapleDonut);
                productRepository.save(chocolateChipCookie);
                productRepository.save(macadamiaCookie);
                productRepository.save(oatmealRaisinCookie);
                productRepository.save(sugarCookie);
                productRepository.save(sourdoughBread);
                productRepository.save(baguette);
                productRepository.save(cinnamonBread);
                productRepository.save(vanillaCupcake);
                productRepository.save(chocolateCupcake);
                productRepository.save(lemonCupcake);
                productRepository.save(redVelvetCupcake);
                productRepository.save(applePie);
                productRepository.save(pumpkinPie);
                productRepository.save(cherryPie);
                productRepository.save(blueberryMuffin);
                productRepository.save(bananaMuffin);
                productRepository.save(chocolateChipMuffin);

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
                                "bonjour@sweetcrust.com");

                Shop tokyoShop = new Shop("SweetCrust Tokyo",
                                Address.builder()
                                                .setStreet("Shibuya Crossing 1")
                                                .setCity("Tokyo")
                                                .setPostalCode("1500001")
                                                .setCountry("Japan")
                                                .build(),
                                "konnichiwa@sweetcrust.com");

                Shop newYorkShop = new Shop("SweetCrust New York",
                                Address.builder()
                                                .setStreet("Broadway 123")
                                                .setCity("New York")
                                                .setPostalCode("10001")
                                                .setCountry("United States")
                                                .build(),
                                "bigapple@sweetcrust.com");

                Shop londonShop = new Shop("SweetCrust London",
                                Address.builder()
                                                .setStreet("Baker Street 221B")
                                                .setCity("London")
                                                .setPostalCode("NW16XE")
                                                .setCountry("United Kingdom")
                                                .build(),
                                "cheerio@sweetcrust.com");

                Shop berlinShop = new Shop("SweetCrust Berlin",
                                Address.builder()
                                                .setStreet("Alexanderplatz 5")
                                                .setCity("Berlin")
                                                .setPostalCode("10178")
                                                .setCountry("Germany")
                                                .build(),
                                "gutentag@sweetcrust.com");

                Shop romeShop = new Shop("SweetCrust Rome",
                                Address.builder()
                                                .setStreet("Via del Corso 99")
                                                .setCity("Rome")
                                                .setPostalCode("00186")
                                                .setCountry("Italy")
                                                .build(),
                                "ciao@sweetcrust.com");

                Shop barcelonaShop = new Shop("SweetCrust Barcelona",
                                Address.builder()
                                                .setStreet("La Rambla 88")
                                                .setCity("Barcelona")
                                                .setPostalCode("08002")
                                                .setCountry("Spain")
                                                .build(),
                                "hola@sweetcrust.com");

                Shop amsterdamShop = new Shop("SweetCrust Amsterdam",
                                Address.builder()
                                                .setStreet("Damrak 12")
                                                .setCity("Amsterdam")
                                                .setPostalCode("1012LG")
                                                .setCountry("Netherlands")
                                                .build(),
                                "hallo@sweetcrust.com");

                shopRepository.save(parisShop);
                shopRepository.save(tokyoShop);
                shopRepository.save(newYorkShop);
                shopRepository.save(londonShop);
                shopRepository.save(berlinShop);
                shopRepository.save(romeShop);
                shopRepository.save(barcelonaShop);
                shopRepository.save(amsterdamShop);

                // B2C
                Cart donutCart = new Cart(LocalDateTime.now());
                donutCart.addCartItem(new CartItem(glazedDonut.getProductId(), regularVariant.getVariantId(), 12,
                                glazedDonut.getBasePrice()));
                donutCart.addCartItem(new CartItem(bostonCreamDonut.getProductId(), regularVariant.getVariantId(), 6,
                                bostonCreamDonut.getBasePrice()));
                donutCart.addCartItem(new CartItem(mapleDonut.getProductId(), regularVariant.getVariantId(), 6,
                                mapleDonut.getBasePrice()));

                Cart cupcakeCart = new Cart(LocalDateTime.now());
                cupcakeCart.addCartItem(new CartItem(vanillaCupcake.getProductId(), regularVariant.getVariantId(),
                                24, vanillaCupcake.getBasePrice()));
                cupcakeCart.addCartItem(new CartItem(chocolateCupcake.getProductId(), regularVariant.getVariantId(),
                                24, chocolateCupcake.getBasePrice()));

                Cart pieCart = new Cart(LocalDateTime.now());
                pieCart.addCartItem(new CartItem(applePie.getProductId(), largeVariant.getVariantId(), 2,
                                applePie.getBasePrice()));
                pieCart.addCartItem(new CartItem(pumpkinPie.getProductId(), largeVariant.getVariantId(), 1,
                                pumpkinPie.getBasePrice()));

                Cart cookieCart = new Cart(LocalDateTime.now());
                cookieCart.addCartItem(new CartItem(chocolateChipCookie.getProductId(),
                                regularVariant.getVariantId(), 36, chocolateChipCookie.getBasePrice()));
                cookieCart.addCartItem(new CartItem(macadamiaCookie.getProductId(), regularVariant.getVariantId(),
                                24, macadamiaCookie.getBasePrice()));

                Cart cakeCart = new Cart(LocalDateTime.now());
                cakeCart.addCartItem(new CartItem(redVelvetCake.getProductId(), largeVariant.getVariantId(), 1,
                                redVelvetCake.getBasePrice()));
                cakeCart.addCartItem(new CartItem(chocolateLavaCake.getProductId(), regularVariant.getVariantId(), 4,
                                chocolateLavaCake.getBasePrice()));

                Cart breadCart = new Cart(LocalDateTime.now());
                breadCart.addCartItem(new CartItem(sourdoughBread.getProductId(), largeVariant.getVariantId(), 3,
                                sourdoughBread.getBasePrice()));
                breadCart.addCartItem(new CartItem(baguette.getProductId(), largeVariant.getVariantId(), 5,
                                baguette.getBasePrice()));

                Cart croissantCart = new Cart(LocalDateTime.now());
                croissantCart.addCartItem(new CartItem(croissant.getProductId(), regularVariant.getVariantId(), 10,
                                croissant.getBasePrice()));
                croissantCart.addCartItem(new CartItem(painAuChocolat.getProductId(), regularVariant.getVariantId(),
                                8, painAuChocolat.getBasePrice()));

                Cart muffinCart = new Cart(LocalDateTime.now());
                muffinCart.addCartItem(new CartItem(blueberryMuffin.getProductId(), regularVariant.getVariantId(), 48,
                                blueberryMuffin.getBasePrice()));
                muffinCart.addCartItem(new CartItem(bananaMuffin.getProductId(), regularVariant.getVariantId(), 36,
                                bananaMuffin.getBasePrice()));
                muffinCart.addCartItem(
                                new CartItem(chocolateChipMuffin.getProductId(), regularVariant.getVariantId(), 24,
                                                chocolateChipMuffin.getBasePrice()));

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
                Cart parisToLondonCart = new Cart(LocalDateTime.now());
                parisToLondonCart.addCartItem(new CartItem(croissant.getProductId(), regularVariant.getVariantId(), 100,
                                croissant.getBasePrice()));
                parisToLondonCart.addCartItem(new CartItem(painAuChocolat.getProductId(), regularVariant.getVariantId(),
                                75, painAuChocolat.getBasePrice()));
                parisToLondonCart.addCartItem(new CartItem(baguette.getProductId(), largeVariant.getVariantId(), 50,
                                baguette.getBasePrice()));

                Cart tokyoToNewYorkCart = new Cart(LocalDateTime.now());
                tokyoToNewYorkCart.addCartItem(new CartItem(glazedDonut.getProductId(), regularVariant.getVariantId(),
                                200, glazedDonut.getBasePrice()));
                tokyoToNewYorkCart.addCartItem(new CartItem(sprinkleDonut.getProductId(), regularVariant.getVariantId(),
                                150, sprinkleDonut.getBasePrice()));

                Cart romeToBarcelonaCart = new Cart(LocalDateTime.now());
                romeToBarcelonaCart.addCartItem(new CartItem(tiramisu.getProductId(), regularVariant.getVariantId(), 30,
                                tiramisu.getBasePrice()));
                romeToBarcelonaCart.addCartItem(new CartItem(applePie.getProductId(), largeVariant.getVariantId(), 20,
                                applePie.getBasePrice()));

                Cart berlinToAmsterdamCart = new Cart(LocalDateTime.now());
                berlinToAmsterdamCart
                                .addCartItem(new CartItem(sourdoughBread.getProductId(), largeVariant.getVariantId(),
                                                40, sourdoughBread.getBasePrice()));
                berlinToAmsterdamCart.addCartItem(new CartItem(cinnamonBread.getProductId(),
                                regularVariant.getVariantId(), 35, cinnamonBread.getBasePrice()));

                Cart newYorkToParisCart = new Cart(LocalDateTime.now());
                newYorkToParisCart.addCartItem(new CartItem(chocolateChipCookie.getProductId(),
                                regularVariant.getVariantId(), 500, chocolateChipCookie.getBasePrice()));
                newYorkToParisCart
                                .addCartItem(new CartItem(blueberryMuffin.getProductId(), regularVariant.getVariantId(),
                                                200, blueberryMuffin.getBasePrice()));

                cartRepository.save(parisToLondonCart);
                cartRepository.save(tokyoToNewYorkCart);
                cartRepository.save(romeToBarcelonaCart);
                cartRepository.save(berlinToAmsterdamCart);
                cartRepository.save(newYorkToParisCart);

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

                Order newYorkToParis = Order.createB2B(
                                LocalDateTime.now().plusDays(6),
                                parisShop.getShopId(),
                                newYorkShop.getShopId(),
                                newYorkToParisCart.getCartId());

                orderRepository.save(parisToLondon);
                orderRepository.save(tokyoToNewYork);
                orderRepository.save(romeToBarcelona);
                orderRepository.save(berlinToAmsterdam);
                orderRepository.save(newYorkToParis);
        }
}