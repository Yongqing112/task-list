package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorInput;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;

public class ErrorService implements ErrorUseCase {

    public String message;

    public ErrorService() {
        this.message = "";
    }

    @Override
    public void execute(ErrorInput input) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("I don't know what the command \"%s\" is.", input.command));
        sb.append(System.lineSeparator());
        setMessage(sb.toString());
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
