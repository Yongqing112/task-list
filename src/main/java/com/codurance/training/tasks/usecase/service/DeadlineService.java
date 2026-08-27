package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineInput;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class DeadlineService implements DeadlineUseCase {

    private String message = "";

    private ToDoListRepository repository;

    public DeadlineService(ToDoListRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute(DeadlineInput deadlineInput) {
        ToDoList toDoList = repository.findById(ToDoListId.of(deadlineInput.toDoListId)).get();

        if (!toDoList.containTask(TaskId.of(deadlineInput.taskId))) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Could not find a project with the name \"%s\".", deadlineInput.taskId));
            sb.append(System.lineSeparator());

            setMessage(sb.toString());
            return;
        }

        toDoList.setDeadline(TaskId.of(deadlineInput.taskId), deadlineInput.deadline);
        repository.save(toDoList);
    }

}
