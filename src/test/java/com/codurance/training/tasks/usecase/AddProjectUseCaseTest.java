package com.codurance.training.tasks.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.service.AddProjectService;

public class AddProjectUseCaseTest {

    @Test
    public void create_project_use_case_success() {
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        ToDoList toDoList = new ToDoList(ToDoListId.of("001"));
        repository.save(toDoList);

        AddProjectUseCase useCase = new AddProjectService(repository);
        AddProjectInput input = new AddProjectInput();
        input.toDoListId = toDoList.geToDoListId().value();
        input.projectName = "p1";
        useCase.execute(input);

        ToDoList readToDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        assertEquals(1, readToDoList.getProjects().size());
        assertEquals(input.projectName, readToDoList.getProjects().get(0).getProjectName().value());
    }
}
