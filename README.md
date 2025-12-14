# SweetCrust - Bakery Management System

SweetCrust is an international modern bakery chain. This backend powers the online ordering system for both retail customers (B2C) and business partners or branches (B2B).

The platform allows customers and bakers to browse products, place orders, and track them through every stage from confirmation to delivery with real-time order status updates.

The system automatically distinguishes between B2C and B2B orders and applies the correct pricing:

- B2C orders use standard selling prices

- B2B (wholesale) orders use purchase prices

Discount codes are supported for promotions and seasonal events.
Active discount code: HAPPYNEWYEAR50 (valid until 31/01/2026)

Each shop is linked to its own inventory, ensuring orders can only be placed for items that are in stock.

Products support multiple variants, each with its own price modifier. Variants may differ in size, flavor, or ingredients, with pricing adjusted accordingly.

## Event Storming
https://miro.com/app/board/uXjVJBbweag=/

### API Documentation

After starting the application, you can access the documentation at:

  - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Project Overview
- **Stack**: Sprint boot (Java), Maven build, JUnit tests
- **Structure**: Layered by bounded contexts (cart, inventory, order, product, shop, user) and by DDD layers (application, domain, infrastructure, presentation, shared).
- **Bootstrapping**: Application entrypoint at `Team10BakeryApplication.java`. App configuration via `application.properties`.

---

# Architecture
- **Formatting**: Google Java Style
- **Layered**: Presentation, Application, Domain & Infrastructure acrocs each context.
- **Clean**: Domain independent; application orchestrates; infrastructure provides communication with database; controllers call inward.
- **Screaming**: Folders (`cart`, `order`, `inventory`, `product`, `shop`, `user`) reflect business context.
- **CQRS**: Commands and queries are serpated:
	- `CartCommandHandler.java`
	- `CartQueryHandler.java`


