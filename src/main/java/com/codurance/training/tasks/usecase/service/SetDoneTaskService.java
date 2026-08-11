package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.project.task.setDone.SetDoneInput;
import com.codurance.training.tasks.usecase.port.in.project.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class SetDoneTaskService implements SetDoneUseCase {

    private ToDoListRepository repository;
    private String message;

    public SetDoneTaskService(ToDoListRepository repository) {
        this.repository = repository;
        this.message = "";
    }

    @Override
    public void execute(SetDoneInput setDoneInput) {
        ToDoList toDoList = repository.findById(ToDoListId.of(setDoneInput.toDoListId)).get();
        TaskId id = TaskId.of(setDoneInput.taskId);

        if (!toDoList.containTask(id)) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Could not find a task with an ID of %s.", id));
            sb.append("\r\n");
            setMessage(sb.toString());
        }

        toDoList.setDone(id, setDoneInput.done);

    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
