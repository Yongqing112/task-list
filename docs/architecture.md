# Architecture

This document describes the architectural design of the Task List project.

The project uses **Clean Architecture** as the primary architectural approach and incorporates concepts from **Domain-Driven Design (DDD)**, **Object-Oriented Design (OOD)**, and **SOLID principles**.

The architecture is intentionally kept simple and will evolve incrementally as new requirements are introduced.

---

## 1. Architectural Goals

The architecture has the following goals:

1. Keep business rules independent from technical details.
2. Keep the Domain model independent from frameworks and infrastructure.
3. Make application behavior easy to test.
4. Make infrastructure replaceable.
5. Keep responsibilities clearly separated.
6. Avoid unnecessary abstractions and over-engineering.
7. Allow the architecture to evolve as requirements become more complex.

The most important goal is:

> Business rules should not depend on technical implementation details.

For example, the business concept of completing a task should not depend on:

* Spring
* JPA
* Hibernate
* SQL
* HTTP
* File systems
* CLI frameworks

---

# 2. Architectural Style

The project follows the principles of **Clean Architecture**.

The architecture can be represented conceptually as:

```text
┌─────────────────────────────────────────────────────────────┐
│                     Infrastructure                          │
│                                                             │
│  Database / Repository Implementation / Framework / CLI     │
│                                                             │
│        ┌─────────────────────────────────────────┐          │
│        │             Application                 │          │
│        │                                         │          │
│        │             Use Cases                   │          │
│        │                                         │          │
│        │      ┌─────────────────────────┐        │          │
│        │      │         Domain          │        │          │
│        │      │                         │        │          │
│        │      │ Entities / Value        │        │          │
│        │      │ Objects / Business      │        │          │
│        │      │ Rules                   │        │          │
│        │      └─────────────────────────┘        │          │
│        └─────────────────────────────────────────┘          │
└─────────────────────────────────────────────────────────────┘

Dependency direction:

Infrastructure → Application → Domain
```

The diagram represents conceptual dependency direction.

The actual implementation may require dependency inversion so that an outer-layer implementation can satisfy an abstraction defined by an inner layer.

---

# 3. The Dependency Rule

The most important architectural rule is:

> Source code dependencies must point inward.

The Domain is the innermost layer.

Therefore:

```text
Infrastructure
      ↓
Application
      ↓
Domain
```

is allowed.

The following is not allowed:

```text
Domain
   ↓
Infrastructure
```

For example, a Domain entity should not contain:

```java
import org.springframework.stereotype.Component;
```

or:

```java
import jakarta.persistence.Entity;
```

or:

```java
import org.springframework.data.jpa.repository.JpaRepository;
```

The Domain should not know that these technologies exist.

---

# 4. Architectural Layers

The project is divided into three major conceptual layers:

```text
Domain
Application
Infrastructure
```

Each layer has a different responsibility.

---

# 5. Domain Layer

## 5.1 Responsibility

The Domain layer contains the core business concepts and business rules.

It represents:

> What the application means.

Typical components include:

* Entities
* Value Objects
* Domain Services
* Domain rules
* Business invariants
* Domain abstractions

The Domain should contain behavior related to the business domain rather than technical implementation details.

---

## 5.2 Domain Independence

The Domain layer must remain independent from infrastructure and frameworks.

The Domain should not depend on:

* Spring
* Spring Boot
* JPA
* Hibernate
* JDBC
* SQL
* HTTP
* JSON
* File systems
* Database drivers
* Web frameworks

This allows the Domain to be tested independently.

For example:

```text
Domain Test
    ↓
Task
    ↓
Business Rule
```

does not require:

```text
Spring Context
Database
HTTP Server
```

---

## 5.3 Entities

An Entity represents a domain concept that has an identity and behavior.

For example:

```text
Task
```

may represent a task in the task list.

A Task may contain:

```text
identity
description
status
```

and behavior such as:

```text
complete()
reopen()
```

The important principle is that the Entity should encapsulate its own business behavior.

Prefer:

```java
task.complete();
```

over:

```java
task.setCompleted(true);
```

when completing a task represents meaningful domain behavior.

---

## 5.4 Encapsulation

Domain objects should protect their internal state.

Avoid exposing mutable state unnecessarily.

For example:

```java
public void setCompleted(boolean completed) {
    this.completed = completed;
}
```

may allow invalid state transitions.

Instead, the domain object can expose operations representing valid business actions:

```java
public void complete() {
    // business rule
}
```

The exact implementation will depend on the requirements.

The goal is:

```text
Object
 ├── State
 └── Behavior
```

rather than:

```text
Object
 └── State only
```

---

# 6. Application Layer

## 6.1 Responsibility

The Application layer contains application-specific business workflows.

It answers:

> How does the application perform a particular use case?

Examples may include:

```text
Create Task
Complete Task
Delete Task
List Tasks
```

The Application layer coordinates the Domain objects required to perform these operations.

---

## 6.2 Use Cases

A Use Case represents an application action.

Conceptually:

```text
Use Case
   │
   ├── Receive input
   │
   ├── Load required domain objects
   │
   ├── Execute domain behavior
   │
   ├── Persist changes
   │
   └── Return output
```

For example:

```text
Complete Task
      │
      ├── Find Task
      │
      ├── Task.complete()
      │
      └── Save Task
```

The Use Case should coordinate these steps.

However, it should not become a place where all business rules are accumulated.

---

# 7. Domain Logic vs Application Logic

One important design decision is distinguishing **Domain Logic** from **Application Logic**.

### Domain Logic

Represents business rules.

Example:

```text
A completed task cannot be completed again.
```

This rule belongs to the Domain.

### Application Logic

Represents the workflow required to perform an operation.

Example:

```text
Find task
→ complete task
→ save task
```

This belongs to the Application layer.

The distinction can be summarized as:

```text
Domain:
"What is a valid Task?"

Application:
"How do I complete a Task?"
```

This distinction prevents Use Cases from becoming procedural containers for business logic.

---

# 8. Infrastructure Layer

## 8.1 Responsibility

The Infrastructure layer contains technical implementation details.

Examples include:

* Database access
* Repository implementations
* File persistence
* Framework integration
* CLI
* HTTP adapters
* External services

Infrastructure answers:

> How is something technically implemented?

---

## 8.2 Infrastructure as an Implementation Detail

Infrastructure should not define business rules.

For example:

```text
DatabaseRepository
```

is responsible for storing and retrieving data.

It should not decide:

```text
Whether a Task can be completed
```

That decision belongs to the Domain.

---

# 9. Repository Abstraction

A common example of dependency inversion is the Repository.

The Application layer may require something like:

```text
TaskRepository
```

The abstraction belongs to an inner layer.

Infrastructure then provides the implementation:

```text
TaskRepository
       ▲
       │
       │ implements
       │
DatabaseTaskRepository
```

Conceptually:

```text
Application
     │
     ▼
TaskRepository
     ▲
     │
Infrastructure
     │
     ▼
Database
```

This allows the Application layer to depend on the abstraction rather than the database implementation.

---

# 10. Why Repository Interfaces Exist

The purpose of a repository abstraction is not:

> Every repository must have an interface.

The actual purpose is:

> Protect the inner layer from infrastructure implementation details.

For example, the Application layer should not need to know whether tasks are stored using:

```text
Memory
Database
File
REST API
```

The Application only needs the required abstraction.

---

# 11. Dependency Inversion

This project applies the **Dependency Inversion Principle** where it provides meaningful architectural value.

Without dependency inversion:

```text
Use Case
   ↓
DatabaseTaskRepository
   ↓
Database
```

The Use Case becomes coupled to the database implementation.

With dependency inversion:

```text
Use Case
   ↓
TaskRepository
   ↑
DatabaseTaskRepository
   ↓
Database
```

The Use Case depends on an abstraction.

The database implementation becomes an outer-layer detail.

---

# 12. Dependency Injection

Dependency Injection is an implementation technique that can be used to provide dependencies to application components.

For example:

```text
CompleteTaskUseCase
        │
        │ depends on
        ▼
TaskRepository
```

The Use Case does not construct a concrete database repository itself.

Instead, the dependency is supplied from outside.

This improves:

* Testability
* Separation of concerns
* Dependency inversion

However, Dependency Injection itself is not the architecture.

A framework such as Spring may provide dependency injection, but the architecture should not depend on Spring.

---

# 13. Framework Independence

Frameworks are considered external implementation details.

For example:

```text
Spring
JPA
Hibernate
```

may eventually be used by the project.

However, the architecture should prevent framework-specific concepts from spreading throughout the Domain.

The preferred dependency direction is:

```text
Application
    ↓
Domain

Infrastructure
    ↓
Framework
```

rather than:

```text
Domain
    ↓
Framework
```

---

# 14. Data Flow

A typical operation may follow this flow:

```text
External Request
      │
      ▼
Infrastructure Adapter
      │
      ▼
Application Use Case
      │
      ▼
Domain Object
      │
      ▼
Repository Abstraction
      │
      ▼
Infrastructure Repository
      │
      ▼
Persistence
```

The exact components may change depending on the interface used by the application.

The important point is that external technical details should not determine the Domain model.

---

# 15. Example: Completing a Task

Consider the requirement:

> Complete a task.

A conceptual flow is:

```text
User
 │
 ▼
Interface Adapter
 │
 ▼
CompleteTaskUseCase
 │
 ├── TaskRepository.findById()
 │
 ▼
Task
 │
 └── complete()
 │
 ▼
TaskRepository.save()
```

The responsibilities are separated.

### Interface Adapter

Receives the external request.

### Use Case

Coordinates the operation.

### Task

Applies domain behavior.

### Repository

Persists the result.

---

# 16. Where Should Logic Go?

When implementing a new feature, ask the following questions.

### Question 1

Is this a business rule?

If yes, consider putting it in the Domain.

---

### Question 2

Is this coordinating multiple domain operations?

If yes, consider putting it in the Application layer.

---

### Question 3

Is this about a database, framework, file system, HTTP, or external system?

If yes, it likely belongs in Infrastructure.

---

### Question 4

Is this only data transformation between external formats?

If yes, it may belong in an adapter or Infrastructure layer depending on the specific responsibility.

---

# 17. Avoid Anemic Domain Models

The project prefers behavior-oriented domain models.

An anemic model often looks like:

```text
Task
 ├── getStatus()
 ├── setStatus()
 ├── getDescription()
 └── setDescription()
```

with all business logic elsewhere:

```text
TaskService
 └── contains all Task business rules
```

This can lead to procedural domain logic.

The preferred direction is to keep meaningful behavior close to the domain object:

```text
Task
 ├── complete()
 ├── reopen()
 └── changeDescription()
```

when those operations represent actual domain concepts.

This does not mean every method belongs in the Entity.

The placement should be determined by responsibility and cohesion.

---

# 18. Avoid Over-Engineering

Clean Architecture does not mean:

> Create an interface for every class.

Nor does it mean:

> Create every possible layer before the requirements exist.

The architecture should evolve with the system.

For example, if there is only one simple implementation and no architectural boundary requires an abstraction, introducing additional interfaces may add unnecessary complexity.

Prefer:

```text
Simple requirement
      ↓
Simple design
      ↓
New requirement
      ↓
Refactor when necessary
```

rather than:

```text
Predict every future requirement
      ↓
Create abstractions everywhere
```

---

# 19. Package Structure

The project may use a package structure similar to:

```text
src/main/java
└── ...
    ├── domain
    │   ├── model
    │   └── repository
    │
    ├── application
    │   └── usecase
    │
    └── infrastructure
        ├── persistence
        └── adapter
```

The exact package names are not architectural principles by themselves.

The important property is that package structure communicates and supports the dependency boundaries.

---

# 20. Package Dependency Rules

The intended dependency relationships are:

```text
Domain
  ↑
Application
  ↑
Infrastructure
```

More explicitly:

```text
Domain
 └── depends on nothing outside Domain

Application
 └── may depend on Domain

Infrastructure
 ├── may depend on Application
 └── may depend on Domain
```

The following dependency should be avoided:

```text
Domain → Infrastructure
```

and:

```text
Domain → Application
```

should only exist if the architecture explicitly defines such a dependency. In the intended design, the Domain remains independent from Application as well.

---

# 21. Testing Architecture

The architecture should make tests easier to write.

The testing strategy follows the dependency direction.

```text
                Tests
                  │
       ┌──────────┼──────────┐
       ▼          ▼          ▼
    Domain    Application Infrastructure
```

---

## 21.1 Domain Tests

Domain tests should focus on business behavior.

Example:

```text
Task.complete()
```

should be testable without:

```text
Database
Spring
HTTP
```

These tests should generally be fast and isolated.

---

## 21.2 Application Tests

Application tests verify use-case behavior.

They may use test doubles for dependencies such as repositories.

Conceptually:

```text
CompleteTaskUseCase
        │
        ▼
FakeTaskRepository
```

This allows the behavior of the Use Case to be tested without requiring a real database.

---

## 21.3 Infrastructure Tests

Infrastructure tests verify technical integration.

Examples:

```text
Repository implementation
Database mapping
SQL behavior
External API integration
```

These tests may require external infrastructure.

---

# 22. Testing Pyramid

The project follows the general principle of having more fast, isolated tests and fewer expensive integration tests.

Conceptually:

```text
          /\
         /  \
        / E2E\
       /------\
      /  Integ \
     /----------\
    / Unit Tests \
   /--------------\
```

Most business behavior should be covered by unit tests.

Integration tests should be used where interaction with real infrastructure provides meaningful value.

---

# 23. SOLID and Architecture

SOLID principles support the architecture but are not the architecture itself.

For example:

### Single Responsibility

Helps maintain clear responsibilities.

### Dependency Inversion

Supports architectural boundaries.

### Interface Segregation

Helps keep abstractions focused.

### Open/Closed

Can help isolate changing behavior.

### Liskov Substitution

Ensures abstractions remain valid contracts.

SOLID should therefore be applied pragmatically.

---

# 24. Design Decision Guidelines

When making a design decision, evaluate it using the following questions:

### Responsibility

> Which object should own this behavior?

### Dependency

> Which layer does this class depend on?

### Business Logic

> Is this a business rule or technical implementation?

### Cohesion

> Does this behavior naturally belong with this object?

### Coupling

> Does this change unnecessarily couple two layers?

### Testability

> Can this behavior be tested without unnecessary infrastructure?

### Complexity

> Is this abstraction actually necessary?

### Future Change

> Which part of the system is likely to change?

These questions should be more important than blindly applying a design pattern.

---

# 25. Architecture Evolution

The architecture is intentionally evolutionary.

The project does not attempt to predict every future requirement.

Instead:

```text
Requirement
    ↓
Simple Design
    ↓
Test
    ↓
New Requirement
    ↓
Identify Design Pressure
    ↓
Refactor
```

This approach allows architectural concepts to be introduced when there is a concrete reason for them.

---

# 26. Refactoring Principles

Refactoring should preserve externally observable behavior while improving the internal design.

Typical reasons for refactoring include:

* Duplicated logic
* Low cohesion
* Excessive coupling
* Anemic domain model
* Infrastructure leaking into Domain
* Large Use Cases
* Excessive conditional logic
* Unclear responsibilities
* Unnecessary abstractions

Before refactoring, tests should provide sufficient confidence that behavior is preserved.

---

# 27. Architecture Review Checklist

Before merging a significant change, review the following.

### Domain

* [ ] Does the Domain contain business rules?
* [ ] Does the Domain avoid framework dependencies?
* [ ] Is domain behavior encapsulated?
* [ ] Are business invariants protected?

### Application

* [ ] Does the Use Case coordinate rather than duplicate domain behavior?
* [ ] Are application workflows clearly expressed?
* [ ] Does the Application depend on abstractions where appropriate?

### Infrastructure

* [ ] Are technical details isolated?
* [ ] Does Infrastructure implement required abstractions?
* [ ] Is database/framework logic kept outside the Domain?

### Dependencies

* [ ] Do dependencies point inward?
* [ ] Does the Domain remain independent?
* [ ] Is dependency inversion used where it provides value?

### Testing

* [ ] Can domain behavior be tested independently?
* [ ] Can use cases be tested without unnecessary infrastructure?
* [ ] Are integration tests used where integration behavior matters?

### Design

* [ ] Does each class have a clear responsibility?
* [ ] Is the design unnecessarily complex?
* [ ] Has an abstraction been introduced for a concrete reason?

---

# 28. Current Architectural Principles

The following principles are considered the project's current architectural rules:

1. **Business rules belong to the Domain.**
2. **Use Cases coordinate application workflows.**
3. **Infrastructure contains technical implementation details.**
4. **Dependencies point toward the Domain.**
5. **The Domain must not depend on frameworks or infrastructure.**
6. **Domain objects should encapsulate meaningful behavior.**
7. **Abstractions should be introduced for a concrete architectural reason.**
8. **Testing should reinforce architectural boundaries.**
9. **Prefer simple designs over speculative abstractions.**
10. **Refactor when new requirements create genuine design pressure.**

---

# 29. Architecture vs Implementation

It is important to distinguish architectural principles from implementation details.

For example:

```text
Architecture:
"Domain must not depend on persistence."

Implementation:
"Use package com.example.domain."
```

The first is an architectural rule.

The second is an implementation choice.

Similarly:

```text
Architecture:
"Application depends on an abstraction."

Implementation:
"Use Java interface TaskRepository."
```

The architecture explains **why**.

The implementation explains **how**.

This distinction should be maintained throughout the project.

---

# 30. Long-Term Direction

As the project evolves, the architecture may introduce additional concepts such as:

* Value Objects
* Domain Services
* Domain Events
* Ports and Adapters
* Application Services
* Command / Query separation
* Transaction boundaries
* Integration testing
* Dependency Injection
* Persistence mapping
* External interfaces

These concepts should only be introduced when the requirements provide a meaningful reason to do so.

The goal is not to maximize the number of architectural patterns.

The goal is to understand **when a particular design becomes necessary and what problem it solves**.

---

# 31. Summary

The architecture can be summarized as:

```text
                    External World
                          │
                          ▼
                ┌───────────────────┐
                │  Infrastructure   │
                │                   │
                │ DB / Framework /  │
                │ Adapters          │
                └─────────┬─────────┘
                          │
                          ▼
                ┌───────────────────┐
                │    Application    │
                │                   │
                │    Use Cases      │
                └─────────┬─────────┘
                          │
                          ▼
                ┌───────────────────┐
                │      Domain       │
                │                   │
                │ Entities / Rules  │
                │ Business Behavior │
                └───────────────────┘
```

The central principle is:

> **The business domain is the core of the application, and technical details should depend on it rather than define it.**

The architecture should evolve through requirements, tests, and refactoring rather than being created entirely in advance.
