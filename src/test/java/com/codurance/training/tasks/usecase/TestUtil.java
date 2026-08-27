package com.codurance.training.tasks.usecase;

import java.time.LocalDateTime;

import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
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

public class TestUtil {

    public static ToDoListRepository createRepository() {
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        return repository;
    }

    public static void run_add_project_usecase(String projectName, ToDoListRepository repository) {
        AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
        AddProjectInput addProjectInput = new AddProjectInput();
        addProjectInput.projectName = projectName;
        addProjectInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addProjectUseCase.execute(addProjectInput);
    }

    public static void run_add_task_usecase(String projectName, String description, ToDoListRepository repository) {
        AddTaskUseCase addTaskUseCase = new AddTaskService(repository);
        AddTaskInput addTaskInput = new AddTaskInput();
        addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addTaskInput.projectName = projectName;
        addTaskInput.description = description;
        addTaskInput.done = false;
        addTaskUseCase.execute(addTaskInput);
    }

    public static void run_set_deadline_usecase(String taskId, LocalDateTime deadline, ToDoListRepository repository) {
        DeadlineUseCase deadlineUseCase = new DeadlineService(repository);
        DeadlineInput deadlineInput = new DeadlineInput();
        deadlineInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        deadlineInput.taskId = taskId;
        deadlineInput.deadline = deadline;
        deadlineUseCase.execute(deadlineInput);
    }
}
