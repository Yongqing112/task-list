package com.codurance.training.tasks.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.io.standard.ToDoListApp;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskInput;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineInput;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.service.AddProjectService;
import com.codurance.training.tasks.usecase.service.AddTaskService;
import com.codurance.training.tasks.usecase.service.DeadlineService;

public class DeadlineUseCaseTest {

    @Test
    public void deadline_usecase() {
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(new ToDoListId(ToDoListApp.DEFAULT_TO_DO_LIST_ID)));

        run_add_project_usecase(repository);

        run_add_task_usecase(repository);

        DeadlineUseCase deadlineUseCase = new DeadlineService(repository);
        DeadlineInput deadlineInput = new DeadlineInput();
        deadlineInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        deadlineInput.taskId = "1";
        deadlineInput.deadline = LocalDateTime.of(2026, 8, 27, 12, 0, 0);
        deadlineUseCase.execute(deadlineInput);

        ToDoList toDoList = repository.findById(new ToDoListId(ToDoListApp.DEFAULT_TO_DO_LIST_ID)).get();

        assertEquals(deadlineInput.deadline, toDoList.getTask(new TaskId("1")).get().getDeadline());

    }

    private void run_add_project_usecase(ToDoListRepository repository) {
        AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
        AddProjectInput addProjectInput = new AddProjectInput();
        addProjectInput.projectName = "deadline";
        addProjectInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addProjectUseCase.execute(addProjectInput);
    }

    private void run_add_task_usecase(ToDoListRepository repository) {
        AddTaskUseCase addTaskUseCase = new AddTaskService(repository);
        AddTaskInput addTaskInput = new AddTaskInput();
        addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addTaskInput.projectName = "deadline";
        addTaskInput.description = "Test deadline";
        addTaskInput.done = false;
        addTaskUseCase.execute(addTaskInput);
    }

}
