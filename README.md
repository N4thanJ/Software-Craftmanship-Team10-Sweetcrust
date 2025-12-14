# Sweetcrust - Bakery Management System

SweetCrust is an international modern bakery management and ordering system designed to enhance the customer experience while streamlining internal operations. The platform allows customers to browse and order bread, pastries, cakes, and custom desserts, either in-store or through online pre-orders. In addition to handling sales, the system provides tools for employee management, enabling managers to schedule staff on fixed or flexible workdays efficiently. SweetCrust also includes inventory management across multiple bakery locations, ensuring optimal stock levels and minimizing waste. 

By combining customer-facing features with robust internal management, SweetCrust supports the growth and smooth operation of a modern bakery on a global scale.

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
- **Package + run**: `./mvn package java -jar target/*.jar`

---

# Architecture
- **Layered**: Presentation -> Application -> Domain -> Infrastructure acrocs each context.
- **Clean**: Domain independent; application orchestrates; infrastructure provides external adapters; controllers call inward.
- **Screaming**: Context folders (`cart`, `order`, `inventory`, `product`, `shop`, `user`) reflect business capabilities.
- **CQRS**: Commands and queries are serpate per context:
	- `CarCommandHandler.java`
	- `CartQueryHandler.java`
	- `OrderCommandHandler.java`
	- `OrderQueryHandler.java`
	- `InventoryItemCommandHandler.java`
- **DDD**: Entities, value objects, policies, domain exceptions per context; shared kernel at `shared`.
- **Domain Events:** Order lifecycle events under `events`.

---

# Flow
- **Request:** Client calls REST endpoint in a controller, e.g. `CartRestController.java`, `OrderRestController.java`, `InventoryRestController.java`.
- **Mapping**: Controllers use mappers and DTOs, e.g. `CartMapper.java` and `presentation/dto`
- **Use Case**: Controllers delegate to `*CommandHandler` or `*QueryHandler` in `application`.
- **Domain**: Handlers involke domain `entities/`, `valueobjects/`, and `policies/` (see order) to enforece rules; domain/application exceptions signal violations.
- **Persistence**: Handlers depend on repositories in `infrastructure`, e.g. `CartRepository.java`, `OrderRepository.java`, `InventoryItemRepository.java`.
- **Response**: Results mapped back to DTOs and returned.

---

# Modules
- **Cart**: 
	- Endpoints in `presentation`; mapping via ``CartMapper.java``.
	- Use cases: CartCommandHandler, CartQueryHandler.
	- persistence: CartRepository, CartitemRepository.
	- Errors: CartDomainException, CartServiceException.
- **Order**:
	- Endpoints: OrderRestController.
	- Use cases: OrderCommandHandler, OrderQueryHandler.
	- Domain: `policies/`, OrderDomainException, events in application/events.
	- Persistence: OrderRepository.
- **Inventory**:
	- Endpoints: InventoryRestController.
	- Use cases: InventoryItemCommandHandler, queries in `application`.
	- Persistence: InvetoryitemRepository.
	- Errors: InvetoryDomainException, InvetoryitemServiceException.
- **Procuct, Shop, User**:
	- Follow the same layered conventions with `application`, `domain`, `infrastructure`, `presentation`.
- **Shared**:
	- Cross-cutting concerns and utility code at `shared`.

---

# Design Patterns
- **Architectural & DDD**:
	- **Layered Architecture:** Clear separation among presentation, application, domain, infrastructure.
	- **Clean Architecture**: Inward dependencies; domain unaffected by frameworks.
	- **CQRS**: Commands and queries with dedicated handlers and often separate request DTOs.
	- **Event-Driven (Domain Events)**: Order events suggest publish/subscribe or lifecycle reactions.
	- **DDD**: Entities, value objects, policies, exceptions; `shared` kernel.
	- **Bounded Context**: One folder per context; minimal coupling via `shared
	- **Aggregates**: Indicated by `Entities/` organization; aggregate roots not explicitly named but implied.

- **Creational**:
	- **Singleton**: Spring beans default to singleton scop (`@Service`, `@Repository`, `@Component`). No classic synchronized/eager-singleton classes needed.

- **Structural**:
	- **Facade**: REST controllers expose a simplified API over domain/application.
	- **Adapter**: Mappers and DTOs adapt HTTP payloads to domain models.

- **Behavioral**: 
	- **Command**: Command objects + `*CommandHandler` per context implement use cases.
	- **Strategy**: Policies under `order/domain/policies` are strategies for interchangeable business rules.
	- **Observer**: `OrderEventPublisher` with subscribe/unsubscripe and ``OrderEventObserver`` implements observer.
	- **State**: Used for order status transtitions.
	- **Iterator**: Standard Java collections/loops evident (e.g. `for (Observer observer : observers)` in `OrderEventPublisher`).

- **Analysis & Modeling Techniques**:
	- **Event Storming**: Structure and domain events align with event storming outcomes; documentation artifacts are not in-repo.
	- **Process Modeling / Big Picture**: Reflected by bounded contexts and policies/events.

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

- **Test & Development Patterns**:
	- **TDD / Red-Green-Refactor**: Tests are organized per context at `team10_bakery`; presence suggests test-first is feasible, though not guaranteed.

---

# Conventions
- **Packages**: `com.sweetcrust.team10_bakery.<context>.<layer>`.
- **Naming**: `*RestController`, `*Mapper`, `*CommandHandler`, `*QueryHandler`, `*Repository`, `DomainException`, `*ServiceException`.
- **DTOs**: Under `presentation/dto`.
- **Annotations**: Standard Sprint (`@RestController`, `@Service`, `@Repository`, `@Component`) and mapping annotations within controllers and mappers.
- **Formatting**: Maven wrapper present; Google Java Formatting.

# Error Handling
- **Domain Exceptions**: Violations of business rules, e.g. `CartDomainException.java`, `OrderDomainException.java`, `InventoryDomainException.java`.
- **Application Exceptions**: Use-case orchestration or infrastructure issues, e.g. `CartServiceException.java`, `OrderServiceException.java`, `InventoryItemServiceException.java`.
- **Controller Advice**: If included under `shared/presentation`, provides unified error responses; otherwise handled per controller.

# Testing
- **Context Test**: `Team10BakeryApplicationTests.java`.
- **Per-Context**: Mirrors `application` and `domain` for `cart`, `order`, `product`, `shop`, `user`.

# Extending
- **New Use Case:** Add request DTO + endpoint in `presentation`, handler under `application/commands` or `application/queries`, domain logic under `domain`, repository/update under `infrastructure`.
- **New Context**: Create a new top-level folder mirroring the layered structure. Leverage `shared` for commen utilities.
- **Events**: For domain decoupling, publish domain events (as in `order`) and subscribe in application services or listeners.
