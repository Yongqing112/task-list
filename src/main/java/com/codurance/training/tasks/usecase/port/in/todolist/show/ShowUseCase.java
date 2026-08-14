package com.codurance.training.tasks.usecase.port.in.todolist.show;

public interface ShowUseCase {
    void setMessage(String message);

    String getMessage();

    ShowOutput execute(ShowInput input);
}
