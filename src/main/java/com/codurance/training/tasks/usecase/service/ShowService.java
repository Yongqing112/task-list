package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.ToDoListDTO;
import com.codurance.training.tasks.usecase.port.ToDoListMapper;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowInput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class ShowService implements ShowUseCase {

    private ToDoListRepository repository;
    private String message;

    public ShowService(ToDoListRepository repository) {
        this.repository = repository;
        this.message = "";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public ShowOutput execute(ShowInput input) {
        ToDoListId toDoListId = ToDoListId.of(input.toDoListId);
        ToDoList toDoList = repository.findById(toDoListId).get();
        ToDoListDTO toDoListDTO = ToDoListMapper.toDTO(toDoList);
        StringBuilder sb = new StringBuilder();

        for (Project project : toDoList.getProjects()) {

            sb.append(project.getProjectName());
            sb.append("\n");
            for (Task task : project.getTasks()) {
                sb.append(String.format("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(),
                        task.getDescription()));
            }
            sb.append("\n");
        }

        setMessage(sb.toString());
        ShowOutput output = new ShowOutput();
        output.toDoListDTO = toDoListDTO;
        output.message = message;
        return output;
    }
}
