# SweetCrust - Bakery Management System

SweetCrust is an international modern bakery chain. This backend serves as the ordering system designed to enhance the customer experience of both regular customers as well as other branches of the SweetCrust chain. The platform allows customers and bakers to browse bakery items and create B2B or B2C online orders. The backend allows tracking throughout the whole process of the order, from confirmation to delivery, an order status is always available. 
The system knows how to differentiate orders an applies the appropriate pricing policy for each type of order. Wholesale orders automatically get purchase prices while normal B2C orders get the normal selling price.

The system also supports the application of discount codes on orders to support promotional events and stimulate orders during specific time periods.
Active discount code: `HAPPYNEWYEAR50` valid until 31/01/2026

Each shop is connected to their own inventory to make sure orders can only be made if the desired products are in stock. 

Products each have multiple variants with their own price modifiers. Some variants might just be an increase or decrease in size whereas other products see changes in flavour or ingredients.

## Event Storming
https://miro.com/app/board/uXjVJBbweag=/

### API Documentation

After starting the application, you can access the documentation at:

  - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Project Overview
- **Stack**: Sprint boot (Java), Maven build, JUnit tests
- **Structure**: Layered by bounded contexts (cart, inventory, order, product, shop, user) and by DDD layers (application, domain, infrastructure, presentation, shared).
- **Bootstrapping**: Application entrypoint at `Team10BakeryApplication.java`. App configuration via `application.properties`.
- **Packaging**: Maven wrapper present (mvnw, mvnw.cmd) for consistent builds without requiring a global Maven install.

---

# Quick Start
- **Build**: `./mvnw clean verify`
- **Run**: `./mvnw spring-boot:run`
- **Test**: `./mvnw test`

---

# Architecture
- **Layered**: Presentation, Application, Domain & Infrastructure acrocs each context.
- **Clean**: Domain independent; application orchestrates; infrastructure provides communication with database; controllers call inward.
- **Screaming**: Context folders (`cart`, `order`, `inventory`, `product`, `shop`, `user`) reflect business capabilities.
- **CQRS**: Commands and queries are serpate per context:
	- `CartCommandHandler.java`
	- `CartQueryHandler.java`
- **DDD**: Entities, value objects, policies, domain exceptions per context; shared kernel at `shared`.
- **Domain Events:** Order lifecycle events under `events`.

---

- **Design Principles**:
	- **SOLID**:
		- **SRP**: Controllers, handlers, repositories, mappers each have narrow responsibilities.
		- **OCP**: New commands/policies extend behaviour without modifying stable classes.
		- **LSP**: Interfaces (repositories/services) enable substitutable implementations.
		- **ISP**: Small, focused interfaces and handlers avoid bloated contracts.
		- **DIP**: Application dpeends on abstractions; Sprint injects concrete infrastructure.
	- **KISS/DRY/YAGNI**: Simple structure, shared kernel for reuse, minimal accidental complexity.
	- **Law of Demeter**: Controllers delegate to handlers; domain objects encapsulate invariants.
	- **Composition over Inheritance**: Prefer assembling behavior via entities/value objects/policies.
	- **Hollywood Principle**: Inversion of control with Spring configuration and annotations.

