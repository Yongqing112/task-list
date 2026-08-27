package com.codurance.training.tasks.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.io.standard.ToDoListApp;
import com.codurance.training.tasks.usecase.port.in.task.today.TodayInput;
import com.codurance.training.tasks.usecase.port.in.task.today.TodayOutput;
import com.codurance.training.tasks.usecase.port.in.task.today.TodayUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.service.TodayService;

public class TodayUseCaseTest {

    @Test
    public void today_usecase() {
        ToDoListRepository repository = TestUtil.createRepository();
        repository.save(new ToDoList(new ToDoListId(ToDoListApp.DEFAULT_TO_DO_LIST_ID)));

        LocalDateTime deadline = LocalDateTime.of(2026, 8, 27, 12, 0, 0);
        TestUtil.run_add_project_usecase("p1", repository);
        TestUtil.run_add_project_usecase("p2", repository);
        TestUtil.run_add_task_usecase("p1", "t1", repository);
        TestUtil.run_add_task_usecase("p1", "t2", repository);
        TestUtil.run_add_task_usecase("p2", "t3", repository);
        TestUtil.run_set_deadline_usecase("1", deadline, repository);
        TestUtil.run_set_deadline_usecase("2", LocalDateTime.of(2026, 8, 28, 12, 0, 0), repository);
        TestUtil.run_set_deadline_usecase("3", deadline, repository);

        TodayUseCase todayUseCase = new TodayService(repository);
        TodayInput input = new TodayInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.today = LocalDateTime.of(2026, 8, 27, 12, 0, 0);

        TodayOutput output = todayUseCase.execute(input);

        assertEquals(2, output.toDoListDTO.projectDTOs.stream().mapToLong(p -> p.taskDTOs.size()).sum());
    }
}
