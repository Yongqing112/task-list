# SQL Reserved Word Conflict Fix

## Problem
When the Spring Boot application started and tried to query the database, it failed with a SQL syntax error due to using a reserved keyword as a column name.

## Error Message
```
Syntax error in SQL statement "select tdlp1_0.id,tdlp1_0.last_task_id,pp1_0.id_fk,pp1_0.project_name,pp1_0.[*]order from to_do_list tdlp1_0 left join project pp1_0 on tdlp1_0.id=pp1_0.id_fk where tdlp1_0.id=?"; expected "identifier"
```

## Root Cause
The `ProjectPO` class used `order` as a column name, which is a reserved keyword in SQL (used for ORDER BY clauses). When Hibernate generated the SQL query, it created invalid syntax because `order` is a reserved word.

## Solution
Changed the column name from `order` to `project_order` in `ProjectPO.java`:

```java
@Column(name = "project_order")
private int order;
```

The Java field name `order` remains unchanged, only the database column name is modified to avoid the SQL reserved word conflict.

## Files Modified
- `src/main/java/com/codurance/training/tasks/usecase/port/out/ProjectPO.java` - Changed column name from "order" to "project_order"

## Key Lessons
1. **Avoid using SQL reserved keywords as column names** in database entities
2. **Common SQL reserved words to avoid**: `order`, `group`, `select`, `where`, `from`, `join`, `insert`, `update`, `delete`, `create`, `drop`, `alter`, etc.
3. **Use @Column annotation** to map Java field names to different database column names when needed
4. **Quote identifiers** when using reserved words is unavoidable (though better to avoid entirely)
5. **Test database schema generation** to catch these issues early in development

## Related Context
This issue was discovered when trying to start the Spring Boot application after adding JPA annotations to enable database persistence. The application failed during the first database query attempt.
