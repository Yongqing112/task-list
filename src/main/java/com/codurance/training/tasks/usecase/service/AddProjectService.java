package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class AddProjectService implements AddProjectUseCase {
    
    private final ToDoListRepository repository;

    public AddProjectService(ToDoListRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void execute(AddProjectInput addProjectInput) {
        ToDoList toDoList = repository.findById(ToDoListId.of(addProjectInput.toDoListId)).get();

        toDoList.addProject(ProjectName.of(addProjectInput.projectName));
    }
}
