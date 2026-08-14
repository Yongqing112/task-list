package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskInput;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class AddTaskService implements AddTaskUseCase {

    private String message = "";
    private final ToDoListRepository repository;

    public AddTaskService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void execute(AddTaskInput addTaskInput) {
        ToDoList toDoList = repository.findById(ToDoListId.of(addTaskInput.toDoListId)).get();
        if (toDoList.getProject(ProjectName.of(addTaskInput.projectName)).isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Could not find a project with the name \"%s\".", addTaskInput.projectName));
            sb.append("\r\n");

            setMessage(sb.toString());
            return;
        }
        toDoList.addTask(ProjectName.of(addTaskInput.projectName), addTaskInput.description, addTaskInput.done);
    }
}
