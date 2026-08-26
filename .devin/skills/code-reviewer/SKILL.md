---
name: code-reviewer
description: Performs comprehensive code review for Java projects following Clean Architecture, DDD, and SOLID principles. Use when the user requests code review, analysis, or evaluation of existing code against architectural standards.
---

# Code Reviewer

This skill performs systematic code review following Clean Architecture, Domain-Driven Design (DDD), Object-Oriented Design (OOD), and SOLID principles. It evaluates code against the project's architectural standards defined in GEMINI.md.

## Core Workflow

Always follow these steps when this skill is active:

### 1. Read Project Specifications
Read and understand the project's architectural guidelines:
- `GEMINI.md` - Project-specific architectural principles and development guidelines
- `README.md` - Project overview and architecture documentation
- Existing code review files in `docs/reviews/` to understand previous review patterns

### 2. Analyze Code Structure
Examine the codebase systematically:
- **Domain Layer**: Check entities, value objects, and domain behavior
- **Application Layer**: Review use cases, services, and application workflows
- **Infrastructure Layer**: Evaluate controllers, repositories, and technical implementations
- **Testing**: Review test coverage and testing strategies

### 3. Apply Architectural Principles
Evaluate code against the following principles:

#### Clean Architecture
- Dependency direction: Infrastructure → Application → Domain
- Domain layer must not depend on frameworks, databases, or infrastructure
- Use dependency inversion where appropriate

#### Behavior-Oriented Design
- Prefer domain behaviors over state manipulation (e.g., `task.complete()` vs `task.setCompleted(true)`)
- Encapsulation of domain invariants
- Meaningful domain operations

#### SOLID Principles
- **Single Responsibility**: Classes should have focused responsibilities
- **Open/Closed**: Design should allow extension without modification
- **Liskov Substitution**: Implementations should honor their abstractions
- **Interface Segregation**: Interfaces should remain focused
- **Dependency Inversion**: High-level policy should depend on abstractions

#### Object-Oriented Design
- High cohesion and low coupling
- Clear responsibilities
- Proper encapsulation
- Avoid anemic domain models

### 4. Identify Issues
Categorize findings by priority:

#### High Priority
- Architectural violations (dependency direction, framework leakage into Domain)
- Incorrect behavior
- Critical design flaws

#### Medium Priority
- Responsibility assignment issues
- Design problems
- Testability concerns

#### Low Priority
- Style and readability
- Minor optimizations
- Naming conventions

### 5. Generate Review Report
Create a comprehensive review document following the format in `docs/reviews/code-review-2026-08-16.md`:

```markdown
# Code Review - [YYYY-MM-DD]

## 1. Architecture & Dependency Management
- **Strength**: [positive observations]
- **Concern**: [architectural issues]
- **Observation**: [notable patterns]

## 2. Domain Layer (Entities & Encapsulation)
- **Critical Issue**: [domain layer problems]
- **Encapsulation Issue**: [encapsulation problems]
- **Design Concern**: [design issues]

## 3. Application Layer (Use Cases & Services)
- **Critical Issue**: [application layer problems]
- **Observation**: [notable patterns]

## 4. Infrastructure Layer
- **Observation**: [infrastructure observations]
- **Strength**: [positive aspects]

## 5. Testing
- **Insufficient Coverage**: [testing gaps]
- **Concern**: [testing issues]

---

## Summary of Priorities

### High Priority
1. [Issue description]
2. [Issue description]

### Medium Priority
3. [Issue description]
4. [Issue description]

### Low Priority
5. [Issue description]
6. [Issue description]

---

## Architectural Principles Referenced

- [List of principles applied]
```

### 6. Save Review Document
Save the review to `docs/reviews/code-review-[YYYY-MM-DD].md` with the current date.

## Guidelines

- **Do not immediately provide complete implementations** unless explicitly requested
- **Explain reasoning** behind design issues rather than just stating problems
- **Prioritize architectural correctness** over stylistic preferences
- **Avoid over-engineering** - recommend simplest solution that satisfies requirements
- **Focus on learning** - explain why a design is appropriate or problematic
- **Reference GEMINI.md** principles when explaining issues

## Review Process

When reviewing code, ask:
1. What is the root problem?
2. Which architectural layer is affected?
3. Are responsibilities correctly assigned?
4. Is the dependency direction correct?
5. Does this abstraction solve an actual problem?
6. Can this behavior be tested independently?
7. Is infrastructure leaking into the domain?
