# Production Environment Bean Conflict Fix

## Problem
When running the Spring Boot application with `mvn spring-boot:run` or `java -jar`, the application failed to start due to Bean conflict.

## Error Message
```
No qualifying bean of type 'com.codurance.training.tasks.usecase.port.out.ToDoListRepository' available: expected single matching bean but found 2: toDoListRepository,toDoListCrudRepository
```

## Root Cause
The `RepositoryInjection` configuration class defined two `ToDoListRepository` beans without specifying which one should be used by default in production environment:
- `toDoListRepository` (using `ToDoListInMemoryRepository`)
- `toDoListCrudRepository` (using `ToDoListCrudRepository`)

When the Spring Boot application tried to autowire `ToDoListRepository` in `ToDoListSpringBootApp`, it couldn't determine which bean to use.

## Solution
Added `@Primary` annotation to the `toDoListCrudRepository` bean in `RepositoryInjection.java`:

```java
@Bean(name = "toDoListCrudRepository")
@Primary
public ToDoListRepository toDoListCrudRepository() {
    return new ToDoListCrudRepository(toDoListCrudRepositoryPeer);
}
```

This ensures that the production environment uses the JPA-based repository by default, while tests can still use the in-memory repository through their specific configuration.

## Files Modified
- `src/main/java/com/codurance/training/tasks/io/springboot/config/RepositoryInjection.java` - Added @Primary annotation and import

## Key Lessons
1. **Use @Primary annotation** when multiple beans of the same type exist and you need to specify a default
2. **Consider environment-specific configurations** - production and test environments may need different bean implementations
3. **Use @Qualifier** when you need to explicitly specify which bean to inject in specific cases
4. **Separate concerns** - keep in-memory repositories for testing and JPA repositories for production

## Related Context
This fix was implemented after adding JPA annotations to PO classes (ProjectPO, TaskPO, ToDoListPO) to enable database persistence. The production environment needed to use the JPA-based repository while tests continue using in-memory storage.
