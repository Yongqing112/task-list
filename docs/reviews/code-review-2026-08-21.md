# Code Review - 2026-08-21

## 1. Architecture & Dependency Management
- **Strength**: The project successfully adheres to Clean Architecture layering (`Domain` ← `Application` ← `Infrastructure`). The dependency direction is correct, with no leakage of framework/infrastructure concerns into the Domain.
- **Strength**: Repository abstraction properly implements Dependency Inversion Principle with interface in Application layer and implementation in Infrastructure layer.
- **Concern**: `ToDoListInMemoryRepository` uses an unnecessary Peer pattern (`ToDoListRepositoryPeer`), adding complexity without clear architectural benefit per GEMINI.md guidelines.

## 2. Domain Layer (Entities & Encapsulation)
- **Critical Issue**: `Task.setDone(boolean done)` violates behavior-oriented design principles (GEMINI.md Section 5). Should use domain behaviors like `task.complete()` and `task.reopen()` instead of state manipulation.
- **Critical Issue**: `Project.setTaskDone(TaskId id, boolean done)` similarly violates behavior-oriented design by calling `task.setDone(done)` instead of delegating to domain behavior.
- **Encapsulation Issue**: `Project.getTasks()` returns the internal mutable list directly, violating encapsulation. Should return an immutable copy or read-only view.
- **Immutability Issue**: `Project.setProjectName()` allows external modification of project name after construction. Project name should be immutable.
- **Design Concern**: `ReadOnlyProject` and `ReadOnlyTask` use inheritance for read-only wrapping, which is fragile. If parent class adds new methods, they must be overridden in subclasses. Consider using interfaces or composition instead.
- **Typo**: `ToDoList.geToDoListId()` has a spelling error, should be `getToDoListId()`.

## 3. Application Layer (Use Cases & Services)
- **Critical Issue**: `ShowService` contains presentation logic (string formatting with StringBuilder) in lines 36-49. This violates separation of concerns - formatting belongs in Presenter layer, not Use Case.
- **Critical Issue**: `AddTaskService` constructs error message strings directly (lines 32-38). Error message formatting should be in Presenter layer.
- **Error Handling Issue**: Both `ShowService` (line 34) and `AddTaskService` (line 31) call `.get()` on Optional without checking, risking NoSuchElementException. Should use `.orElseThrow()` or proper error handling.
- **Observation**: Use Case services contain both workflow logic and message handling, mixing responsibilities that should be separated.

## 4. Infrastructure Layer
- **Observation**: `ToDoListConsoleController` is tightly coupled to all available use cases. As commands grow, consider applying a command registry or strategy pattern to decouple.
- **Strength**: Spring configuration uses constructor injection properly in `RepositoryInjection` and `UseCaseInjection`.

## 5. Testing
- **Insufficient Coverage**: `ToDoListTest.java` only contains one test case. Missing tests for:
  - Task addition
  - Task completion/reopening
  - Project-task relationships
  - Edge cases and boundary conditions
- **Concern**: Per GEMINI.md Section 12, Domain behavior should be testable without infrastructure, but current test coverage is minimal.

---

## Summary of Priorities

### High Priority
1. **Refactor Task Behavior**: Change `Task.setDone(boolean)` to behavior-oriented methods `complete()` and `reopen()`
2. **Fix Typo**: Correct `ToDoList.geToDoListId()` to `getToDoListId()`
3. **Fix Optional Handling**: Replace `.get()` calls with proper error handling using `.orElseThrow()`

### Medium Priority
4. **Separate Presentation Logic**: Move formatting logic from `ShowService` to Presenter layer
5. **Fix Encapsulation**: Make `Project.getTasks()` return immutable copy or read-only view
6. **Remove Mutable Setter**: Remove or rationalize `Project.setProjectName()` to ensure project name immutability
7. **Separate Error Handling**: Move error message formatting from Use Cases to Presenter layer

### Low Priority
8. **Refactor ReadOnly Pattern**: Replace inheritance-based ReadOnly wrappers with interface or composition-based approach
9. **Simplify Repository**: Remove unnecessary Peer pattern from `ToDoListInMemoryRepository`
10. **Increase Domain Test Coverage**: Add comprehensive tests for Domain layer behavior
11. **Decouple Controller**: Consider command registry or strategy pattern for `ToDoListConsoleController` as use cases grow

---

## Architectural Principles Referenced

- **Clean Architecture**: Dependency direction (Infrastructure → Application → Domain)
- **Behavior-Oriented Design**: Domain objects should express meaningful operations, not expose state manipulation
- **Encapsulation**: Protect internal state and invariants
- **Separation of Concerns**: Each layer should have clear, focused responsibilities
- **Dependency Inversion**: High-level policy should not depend on low-level implementation details
- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution
