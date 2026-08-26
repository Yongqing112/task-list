package com.codurance.training.tasks.usecase.port;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.usecase.port.out.TaskPO;

public class TaskMapperTest {

    @Test
    public void testToDtoMethod() {
        // Create an instance of a task
        Task task = new Task(TaskId.of("1"), "This is a test task.", false);

        // Convert the task instance to a TaskDto
        TaskDTO taskDto = TaskMapper.toDTO(task);

        // Assert that the taskDto's fields match the original task instance's fields
        assertEquals("1", taskDto.taskId);
        assertEquals("This is a test task.", taskDto.description);
        assertEquals(false, taskDto.done);
    }

    @Test
    void testToDomain() {
        // Arrange
        String id = "testId";
        String description = "test description";
        Boolean done = false;
        TaskPO taskPo = new TaskPO();
        taskPo.setTaskId(id);
        taskPo.setDescription(description);
        taskPo.setDone(done);

        // Act
        Task task = TaskMapper.toDomain(taskPo);

        // Assert
        assertNotNull(task);
        assertEquals(id, task.getId().value());
        assertEquals(description, task.getDescription());
    }

    @Test
    public void givenTask_whenToPo_thenReturnTaskPo() {
        // Arrange
        TaskId taskId = TaskId.of("100");
        String description = "Test description";
        boolean isDone = false;
        Task task = new Task(taskId, description, isDone);

        // Act
        TaskPO result = TaskMapper.toPO(task);

        // Assert
        assertEquals(taskId.value(), result.getTaskId());
        assertEquals(description, result.getDescription());
        assertEquals(isDone, result.getDone());
    }
}