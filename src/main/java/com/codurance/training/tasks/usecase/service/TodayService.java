package com.codurance.training.tasks.usecase.service;

import java.util.ArrayList;
import java.util.List;

import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.ToDoListMapper;
import com.codurance.training.tasks.usecase.port.in.task.today.TodayInput;
import com.codurance.training.tasks.usecase.port.in.task.today.TodayOutput;
import com.codurance.training.tasks.usecase.port.in.task.today.TodayUseCase;
import com.codurance.training.tasks.usecase.port.out.ProjectPO;
import com.codurance.training.tasks.usecase.port.out.TaskPO;
import com.codurance.training.tasks.usecase.port.out.ToDoListPO;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class TodayService implements TodayUseCase {
    private ToDoListRepository repository;

    public TodayService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodayOutput execute(TodayInput input) {
        ToDoListPO toDoListPo = ToDoListMapper.toPO(repository.findById(ToDoListId.of(input.toDoListId)).get());

        ToDoListPO resultToDoListPo = new ToDoListPO();
        resultToDoListPo.setId(toDoListPo.getId());
        resultToDoListPo.setLastTaskId(toDoListPo.getLastTaskId());

        List<ProjectPO> projectPOs = new ArrayList<>();

        for (ProjectPO project : toDoListPo.getProjectPOs()) {
            List<TaskPO> tasks = project.getTaskPOs();
            tasks.removeIf(t -> t.getDeadline() == null || null == input.today
                    || !(t.getDeadline().toLocalDate().equals(input.today.toLocalDate())));

            project.setTaskPOs(tasks);
            projectPOs.add(project);
        }

        resultToDoListPo.setProjectPOs(projectPOs);
        TodayOutput todayOutput = new TodayOutput();
        todayOutput.toDoListDTO = ToDoListMapper.toDTO(ToDoListMapper.toDomain(resultToDoListPo));
        return todayOutput;
    }

}
