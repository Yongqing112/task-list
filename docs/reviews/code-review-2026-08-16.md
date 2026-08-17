# Code Review - 2026-08-16

## 1. Architecture & Dependency Management
- **Strength**: The project successfully adheres to Clean Architecture layering (`Domain` ← `Application` ← `Infrastructure`). The dependency direction is correct, with no leakage of framework/infrastructure concerns into the Domain.
- **Observation**: `TaskList.java` serves as the central orchestration point for dependency instantiation. While simple now, this class will become a "God" object for configuration if the application grows.

## 2. Domain Layer (Entities & Encapsulation)
- **Concern**: The use of `ReadOnlyProject` and `ReadOnlyTask` wrappers to enforce immutability adds significant complexity. These subclasses introduce an inheritance hierarchy that may not be necessary.
- **Suggestion**: Consider if the Domain models can be refactored to be naturally immutable using modern Java records or better encapsulation, eliminating the need for separate "Read Only" types.

## 3. Application Layer (Use Cases & Services)
- **Observation**: Services like `AddTaskService` currently contain both the application workflow logic and the logic for generating error messages (e.g., formatting the "Could not find project" string).
- **Architectural Principle**: The Application layer should focus on workflow. Formatting specific error messages for the user interface belongs to the Presenter/Infrastructure layer.
- **Recommendation**: Define a Result/Response abstraction for use cases (e.g., `AddTaskResult`) that indicates success or failure. Let the Presenter decide how to display the error, keeping the Use Case independent of the UI format.

## 4. Infrastructure Layer
- **Observation**: `ToDoListConsoleController` is tightly coupled to all available use cases.
- **Suggestion**: As the number of commands grows, consider applying a pattern (like a command registry or strategy pattern) to decouple the controller from the individual use case services.

---

## Summary of Priorities

1.  **Refactor Error Handling**: Decouple error message formatting from Application services.
2.  **Evaluate Encapsulation Strategy**: Simplify the Domain layer by reconsidering the need for `ReadOnly` wrapper classes.
3.  **Monitor `TaskList`**: Keep an eye on dependency configuration growth as new features are added.
