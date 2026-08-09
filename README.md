# Task List

A Java implementation of the [Task List Kata](https://kata-log.rocks/task-list-kata), created as a hands-on project for learning **Clean Architecture**, **Domain-Driven Design (DDD)**, **Object-Oriented Design (OOD)**, **SOLID principles**, and **automated testing**.

The goal of this project is not simply to build a working task list application. Instead, the project is used as a long-term learning exercise to understand **why software is designed in a particular way**, how architectural boundaries are established, and how design decisions evolve as requirements become more complex.

---

## Learning Goals

This project focuses on learning the following concepts through implementation:

* Clean Architecture
* Domain-Driven Design (DDD)
* Object-Oriented Design (OOD)
* SOLID principles
* Separation of concerns
* Dependency inversion
* Dependency injection
* Use Case design
* Domain modeling
* Repository pattern
* Automated testing
* Refactoring
* Test-driven development (TDD)
* Architectural boundaries

The implementation will evolve gradually rather than attempting to introduce every architectural pattern from the beginning.

---

## Project Source

This project is based on the following kata:

* [Task List Kata](https://kata-log.rocks/task-list-kata)

The original kata provides a simple task-list application as a starting point for practicing software design and refactoring.

This repository is an independent learning implementation inspired by that kata.

---

## Why This Project?

A small application such as a task list is useful for studying software architecture because the business domain is simple enough to understand while still allowing architectural complexity to emerge naturally.

For example, a simple requirement such as:

> Add a task to the task list.

can initially be implemented with very little code.

As the requirements evolve, however, questions begin to appear:

* Where should the task creation logic live?
* Who is responsible for validating a task?
* Should the domain object know about persistence?
* How should a use case communicate with a repository?
* Should the repository be an interface?
* Which layer should depend on which?
* How can the domain be tested without a database?
* What happens when an infrastructure technology changes?
* How can the application be extended without modifying existing business rules?

These questions are the main learning focus of this project.

---

## Architecture

The project follows the principles of **Clean Architecture**.

The fundamental dependency rule is:

> Dependencies should point toward the application core.

A simplified view of the architecture is:

```text
                ┌───────────────────────┐
                │     Infrastructure    │
                │                       │
                │ Database / Framework  │
                │ File System / CLI     │
                └───────────┬───────────┘
                            │
                            ▼
                ┌───────────────────────┐
                │      Application      │
                │                       │
                │      Use Cases        │
                └───────────┬───────────┘
                            │
                            ▼
                ┌───────────────────────┐
                │        Domain         │
                │                       │
                │ Entities / Rules      │
                │ Business Concepts     │
                └───────────────────────┘
```

The important principle is that the **Domain layer should not depend on Infrastructure or Frameworks**.

For example:

```text
Domain
  ↓
Application
  ↓
Infrastructure
```

is conceptually different from:

```text
Infrastructure
  ↓
Application
  ↓
Domain
```

The implementation will use dependency inversion where necessary so that the inner layers remain independent of external technologies.

---

## Architectural Layers

### Domain

The Domain layer represents the core business concepts and business rules.

Typical responsibilities include:

* Entities
* Value Objects
* Domain rules
* Domain behavior
* Business invariants

The Domain layer should not depend on:

* Spring
* JPA
* Hibernate
* Database implementations
* HTTP
* File systems
* External frameworks

The goal is for the domain model to remain understandable and testable independently of technical infrastructure.

---

### Application

The Application layer coordinates application-specific behavior through **Use Cases**.

Typical responsibilities include:

* Executing use cases
* Coordinating domain objects
* Defining application workflows
* Depending on abstractions rather than infrastructure implementations

For example:

```text
CreateTask
CompleteTask
ListTasks
DeleteTask
```

A use case should coordinate the operation without containing infrastructure-specific implementation details.

---

### Infrastructure

The Infrastructure layer contains technical implementation details.

Examples include:

* Database access
* Repository implementations
* File persistence
* Framework integration
* Command-line interfaces
* External services

Infrastructure is considered an implementation detail of the application.

The goal is to prevent these technical concerns from leaking into the Domain layer.

---

## Dependency Rule

One of the most important rules of this project is:

```text
Outer layers may depend on inner layers.

Inner layers should not depend on outer layers.
```

For example:

```text
Infrastructure
      │
      ▼
Application
      │
      ▼
Domain
```

A database implementation may depend on a domain repository abstraction.

However, the Domain should not import a database class.

For example, this should be avoided:

```java
import org.springframework.data.jpa.repository.JpaRepository;
```

inside the Domain layer.

The purpose is not simply to follow a convention. It is to keep business rules independent from technical implementation details.

---

## Object-Oriented Design

This project emphasizes **behavior-oriented object design** rather than procedural data manipulation.

For example, instead of exposing internal state and allowing external code to manipulate it:

```java
task.setCompleted(true);
```

the design may prefer behavior expressed through the domain object:

```java
task.complete();
```

The difference is important.

The first approach exposes state manipulation.

The second approach expresses a domain operation.

This project therefore encourages:

* Encapsulation
* High cohesion
* Meaningful domain behavior
* Small and focused objects
* Minimizing unnecessary setters
* Avoiding procedural domain logic

---

## SOLID Principles

SOLID principles are used as design guidelines rather than rigid rules.

### Single Responsibility Principle

A class should have a focused responsibility.

For example, a repository should not also contain task validation and presentation logic.

---

### Open/Closed Principle

The design should allow behavior to be extended without unnecessarily modifying stable existing code.

This principle will be evaluated when the requirements become more complex.

---

### Liskov Substitution Principle

Implementations of abstractions should remain substitutable for their contracts.

This is particularly relevant when repository abstractions have multiple implementations.

---

### Interface Segregation Principle

Interfaces should remain focused on the behavior required by their clients.

The project avoids creating large interfaces merely for the sake of abstraction.

---

### Dependency Inversion Principle

High-level business logic should depend on abstractions rather than concrete infrastructure implementations.

For example:

```text
Use Case
   │
   ▼
Repository Interface
   ▲
   │
Repository Implementation
```

The concrete repository implementation is an outer-layer concern.

---

## Testing Strategy

Testing is an important part of this project.

The primary goal is not simply to maximize test coverage, but to understand **what should be tested at each architectural boundary**.

### Domain Tests

Domain tests should focus on business behavior.

They should ideally require minimal infrastructure.

For example:

```text
Task
 ├── complete()
 ├── reopen()
 └── business rules
```

These behaviors can be tested without a database or framework.

---

### Application Tests

Application tests focus on use cases.

They verify that the application correctly coordinates:

```text
Use Case
   ↓
Domain
   ↓
Repository abstraction
```

Infrastructure implementations should generally not be required for basic use-case tests.

---

### Infrastructure Tests

Infrastructure tests verify technical implementations such as:

* Persistence
* Repository implementations
* External integrations

These tests may require infrastructure-specific dependencies.

---

## Development Philosophy

This project intentionally avoids introducing abstractions without a concrete reason.

The guiding principle is:

> Do not add architecture merely because a pattern exists.

For example, the following is not automatically better:

```text
Task
 ↓
TaskInterface
 ↓
TaskService
 ↓
TaskManager
 ↓
TaskHandler
```

If the application only needs a simple `Task` entity, unnecessary abstractions increase complexity without providing architectural value.

Instead, abstractions should be introduced when they provide a clear benefit such as:

* Dependency inversion
* Multiple implementations
* Isolation of infrastructure
* Improved testability
* Clear architectural boundaries
* Meaningful domain concepts

---

## Technology Stack

Current technologies:

| Technology | Purpose                         |
| ---------- | ------------------------------- |
| Java       | Programming language            |
| Maven      | Build and dependency management |
| JUnit      | Automated testing               |
| Git        | Version control                 |

Additional frameworks or infrastructure may be introduced later as the project evolves.

When introducing new technologies, the architectural boundaries should remain clear.

---

## Project Structure

The exact package structure may evolve as the architecture develops.

The intended conceptual structure is:

```text
src
├── main
│   └── java
│       └── ...
│           ├── domain
│           ├── application
│           └── infrastructure
│
└── test
    └── java
        └── ...
```

The package structure is considered an implementation detail of the architectural boundaries.

---

## Development Workflow

The project follows an incremental development process.

A typical development cycle is:

```text
Requirement
    ↓
Test
    ↓
Domain / Use Case Design
    ↓
Implementation
    ↓
Refactoring
    ↓
Architecture Review
```

Each feature should be used as an opportunity to examine the design rather than simply adding code.

Important questions during development include:

* Which layer owns this behavior?
* Is this business logic or technical logic?
* Does this class have a clear responsibility?
* Is this dependency pointing in the correct direction?
* Is this abstraction necessary?
* Can this behavior be tested independently?
* Is infrastructure leaking into the domain?
* Can the design be simplified?

---

## AI-Assisted Development

AI may be used as a development and learning assistant.

However, the objective is to understand the design rather than blindly accept generated implementations.

When using AI, the preferred workflow is:

```text
Problem
  ↓
Understand the root cause
  ↓
Identify the architectural concept
  ↓
Evaluate possible designs
  ↓
Implement
  ↓
Test
  ↓
Review the design
```

AI suggestions should therefore be evaluated based on:

* Architectural correctness
* Dependency direction
* Domain boundaries
* Object-oriented design
* Testability
* Simplicity
* Maintainability

The project should avoid treating AI-generated code as automatically correct.

---

## Learning Approach

The project will be developed incrementally.

Rather than implementing the complete architecture immediately, each requirement will be used to introduce architectural concepts when they become necessary.

For example:

```text
Basic Task
    ↓
Domain Behavior
    ↓
Use Case
    ↓
Repository Abstraction
    ↓
Dependency Inversion
    ↓
Infrastructure
    ↓
Refactoring
```

This allows the architecture to evolve from actual requirements instead of being imposed prematurely.

---

## What This Project Is Not

This project is not intended to be:

* A production-ready task management system
* A demonstration of every possible design pattern
* A framework showcase
* An example of maximum abstraction
* A collection of unrelated architectural patterns

The primary purpose is **learning software design through implementation and refactoring**.

---

## Reference

Original kata:

[Task List Kata](https://kata-log.rocks/task-list-kata)

---

## License

This project is primarily a personal learning project based on the Task List Kata.

Refer to the original kata repository and this repository's license information for licensing details.
