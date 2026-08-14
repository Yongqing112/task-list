package com.codurance.training.tasks.usecase.port;

import java.util.List;

import com.codurance.training.tasks.entity.Task;

public class TaskMapper {

    public static List<TaskDTO> toDTO(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::toDTO).toList();
    }

    public static TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.taskId = task.getId().value();
        taskDTO.description = task.getDescription();
        taskDTO.done = task.isDone();

        return taskDTO;
    }
}
