package com.codurance.training.tasks.usecase.port.in.project.task.setDone;

public interface SetDoneUseCase {
    void setMessage(String message);
    String getMessage();
    void execute(SetDoneInput setDoneInput);
}
