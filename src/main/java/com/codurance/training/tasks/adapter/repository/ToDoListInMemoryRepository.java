package com.codurance.training.tasks.adapter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListInMemoryRepository implements ToDoListRepository{
    
    private final List<ToDoList> store;

    public ToDoListInMemoryRepository() {
        this.store = new ArrayList<>();
    }

    @Override
    public void save(ToDoList toDoList) {
        store.add(toDoList);
    }

    @Override
    public void delete(ToDoList toDoList) {
        store.removeIf(x -> x.geToDoListId().equals(toDoList.geToDoListId()));
    }

    @Override
    public Optional<ToDoList> findById(ToDoListId toDoListId) {
        return store.stream().filter(x -> x.geToDoListId().equals(toDoListId)).findFirst();
    }
}
