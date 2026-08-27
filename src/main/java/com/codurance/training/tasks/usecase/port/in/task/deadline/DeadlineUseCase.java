package com.codurance.training.tasks.usecase.port.in.task.deadline;

public interface DeadlineUseCase {
    void setMessage(String message);

    String getMessage();

    void execute(DeadlineInput deadlineInput);

}