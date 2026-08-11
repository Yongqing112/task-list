package com.codurance.training.tasks.usecase.port.out;

import java.util.Optional;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;

public interface ToDoListRepository {

    void save(ToDoList toDoList);

    void delete(ToDoList toDoList);

    Optional<ToDoList> findById(ToDoListId toDoListId);
}
