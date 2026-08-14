package com.codurance.training.tasks.usecase.port.in.todolist.error;

public interface ErrorUseCase {
    void setMessage(String message);

    String getMessage();

    void execute(ErrorInput input);
}
