package com.codurance.training.tasks.usecase.port.in.task.deadline;

import java.time.LocalDateTime;

public class DeadlineInput {
    public String toDoListId;
    public String taskId;
    public LocalDateTime deadline;
}
