# Spring Boot Test Bean Resolution Fix

## Problem Description
The `SpringBootApplicationTest` was failing during execution due to Spring Bean configuration issues.

### Error Messages
1. **Initial Error**: `No qualifying bean of type 'com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer' available`
2. **Second Error**: `expected single matching bean but found 2: toDoListRepository,toDoListCrudRepository`
3. **Third Error**: `No qualifying bean of type 'com.codurance.training.tasks.usecase.port.out.ToDoListRepository' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Qualifier("toDoListCrudRepository")}`

## Root Cause Analysis

### Issue 1: Missing @Component Annotation
- `ToDoListInMemoryRepositoryPeer` class lacked the `@Component` annotation
- Spring's component scan could not register it as a Bean
- `RepositoryInjection` configuration class tried to autowire this class but failed

### Issue 2: Bean Conflict
- `RepositoryInjection` configuration class defined two `ToDoListRepository` beans:
  - `toDoListRepository` (using `ToDoListInMemoryRepository`)
  - `toDoListCrudRepository` (using `ToDoListCrudRepository`)
- Test constructor injection of `ToDoListRepository` caused ambiguity - Spring couldn't determine which bean to use

### Issue 3: Wrong Bean Dependency
- `UseCaseInjection` configuration class used `@Qualifier("toDoListCrudRepository")` to inject the repository
- Test needed to use in-memory repository, not the CRUD repository
- This created a dependency mismatch

## Solution

### Step 1: Add @Component Annotation
Added `@Component` annotation to `ToDoListInMemoryRepositoryPeer`:

```java
@Component
public class ToDoListInMemoryRepositoryPeer implements ToDoListRepositoryPeer {
    // ...
}
```

### Step 2: Exclude Conflicting Configurations in Test Context
Modified `JpaApplicationTestContext` to exclude the following configuration classes:

```java
@ComponentScan(basePackages={"com.codurance.training.tasks"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= ToDoListSpringBootApp.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= com.codurance.SpringBootApplicationTest.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= com.codurance.training.tasks.io.springboot.config.RepositoryInjection.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= com.codurance.training.tasks.io.springboot.config.UseCaseInjection.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= com.codurance.training.tasks.io.springboot.config.ToDoListDataSourceConfiguration.class)})
```

### Step 3: Create Test-Specific Beans
Added bean definitions in `JpaApplicationTestContext` for test-specific configuration:

```java
@Bean
@Primary
public ToDoListRepository toDoListRepository(ToDoListRepositoryPeer toDoListRepositoryPeer) {
    return new ToDoListInMemoryRepository(toDoListRepositoryPeer);
}

@Bean
public AddProjectUseCase addProjectUseCase(ToDoListRepository toDoListRepository) {
    return new AddProjectService(toDoListRepository);
}

@Bean
public AddTaskUseCase addTaskUseCase(ToDoListRepository toDoListRepository) {
    return new AddTaskService(toDoListRepository);
}

@Bean
public SetDoneUseCase setDoneUseCase(ToDoListRepository toDoListRepository) {
    return new SetDoneTaskService(toDoListRepository);
}

@Bean
public ErrorUseCase errorUseCase() {
    return new ErrorService();
}

@Bean
public ShowUseCase showUseCase(ToDoListRepository toDoListRepository) {
    return new ShowService(toDoListRepository);
}

@Bean
public HelpUseCase helpUseCase() {
    return new HelpService();
}
```

## Key Lessons

1. **Always add @Component or @Service annotations** to classes that need to be Spring Beans when using component scanning
2. **Avoid bean conflicts** by using @Primary or @Qualifier when multiple beans of the same type exist
3. **Separate test and production configurations** by excluding production-specific configurations in test contexts
4. **Manually create beans in test configurations** when you need specific implementations different from production

## Files Modified

1. `src/main/java/com/codurance/training/tasks/adapter/repository/ToDoListInMemoryRepositoryPeer.java` - Added @Component annotation
2. `src/test/java/com/codurance/training/tasks/config/JpaApplicationTestContext.java` - Excluded conflicting configurations and added test-specific bean definitions

## Related Context

This fix was implemented after adding JPA annotations to PO classes (ProjectPO, TaskPO, ToDoListPO) to enable database persistence. The test configuration needed to be updated to continue using in-memory storage for testing while the production configuration uses JPA/Database storage.
