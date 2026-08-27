package com.codurance.training.tasks.usecase.port;

import java.util.List;

import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.usecase.port.out.TaskPO;

public class TaskMapper {

    public static List<TaskDTO> toDTO(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::toDTO).toList();
    }

    public static TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.taskId = task.getId().value();
        taskDTO.description = task.getDescription();
        taskDTO.done = task.isDone();
        taskDTO.deadline = task.getDeadline();

        return taskDTO;
    }

    public static Task toDomain(TaskPO taskPO) {
        return new Task(TaskId.of(taskPO.getTaskId()), taskPO.getDescription(), taskPO.getDone(), taskPO.getDeadline());
    }

    public static List<Task> toDomain(List<TaskPO> tasks) {
        return tasks.stream().map(TaskMapper::toDomain).toList();
    }

    public static TaskPO toPO(Task task) {
        return new TaskPO(task.getId().value(), task.getDescription(), task.isDone(), task.getDeadline());
    }

    public static List<TaskPO> toPO(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::toPO).toList();
    }
}
