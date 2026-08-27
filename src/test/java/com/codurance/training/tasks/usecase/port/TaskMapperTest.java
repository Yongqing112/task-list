package com.codurance.training.tasks.usecase.port;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.usecase.port.out.TaskPO;

public class TaskMapperTest {

    @Test
    public void testToDtoMethod() {
        // Create an instance of a task
        Task task = new Task(TaskId.of("1"), "This is a test task.", false, LocalDateTime.of(2026, 8, 27, 12, 0, 0));

        // Convert the task instance to a TaskDto
        TaskDTO taskDto = TaskMapper.toDTO(task);

        // Assert that the taskDto's fields match the original task instance's fields
        assertEquals("1", taskDto.taskId);
        assertEquals("This is a test task.", taskDto.description);
        assertEquals(false, taskDto.done);
        assertEquals(LocalDateTime.of(2026, 8, 27, 12, 0, 0), taskDto.deadline);
    }

    @Test
    void testToDomain() {
        // Arrange
        String id = "testId";
        String description = "test description";
        Boolean done = false;
        LocalDateTime deadline = LocalDateTime.of(2026, 8, 27, 12, 0, 0);
        TaskPO taskPo = new TaskPO();
        taskPo.setTaskId(id);
        taskPo.setDescription(description);
        taskPo.setDone(done);
        taskPo.setDeadline(deadline);

        // Act
        Task task = TaskMapper.toDomain(taskPo);

        // Assert
        assertNotNull(task);
        assertEquals(id, task.getId().value());
        assertEquals(description, task.getDescription());
        assertEquals(deadline, task.getDeadline());
    }

    @Test
    public void givenTask_whenToPo_thenReturnTaskPo() {
        // Arrange
        TaskId taskId = TaskId.of("100");
        String description = "Test description";
        boolean isDone = false;
        LocalDateTime deadline = LocalDateTime.of(2026, 8, 27, 12, 0, 0);
        Task task = new Task(taskId, description, isDone, deadline);

        // Act
        TaskPO result = TaskMapper.toPO(task);

        // Assert
        assertEquals(taskId.value(), result.getTaskId());
        assertEquals(description, result.getDescription());
        assertEquals(isDone, result.getDone());
        assertEquals(deadline, result.getDeadline());
    }
}