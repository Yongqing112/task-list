package com.codurance.training.tasks.usecase.port.in.project.task.add;

public interface AddTaskUseCase {
    void setMessage(String message);
    String getMessage();
    void execute(AddTaskInput addTaskInput);
}
