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
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineInput;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.service.DeadlineService;

public class DeadlineUseCaseTest {

    @Test
    public void deadline_usecase() {
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(new ToDoListId(ToDoListApp.DEFAULT_TO_DO_LIST_ID)));

        TestUtil.run_add_project_usecase("deadline", repository);

        TestUtil.run_add_task_usecase("deadline", "Test deadline", repository);

        DeadlineUseCase deadlineUseCase = new DeadlineService(repository);
        DeadlineInput deadlineInput = new DeadlineInput();
        deadlineInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        deadlineInput.taskId = "1";
        deadlineInput.deadline = LocalDateTime.of(2026, 8, 27, 12, 0, 0);
        deadlineUseCase.execute(deadlineInput);

        ToDoList toDoList = repository.findById(new ToDoListId(ToDoListApp.DEFAULT_TO_DO_LIST_ID)).get();

        assertEquals(deadlineInput.deadline, toDoList.getTask(new TaskId("1")).get().getDeadline());

    }

}
