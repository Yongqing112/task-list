package com.codurance.training.tasks.adapter.repository;

import java.util.Optional;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.ToDoListMapper;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListInMemoryRepository implements ToDoListRepository {

    private final ToDoListRepositoryPeer toDoListRepositoryPeer;

    public ToDoListInMemoryRepository(ToDoListRepositoryPeer toDoListRepositoryPeer) {
        this.toDoListRepositoryPeer = toDoListRepositoryPeer;
    }

    @Override
    public void save(ToDoList toDoList) {
        this.toDoListRepositoryPeer.save(ToDoListMapper.toPO(toDoList));
    }

    @Override
    public void delete(ToDoList toDoList) {
        this.toDoListRepositoryPeer.delete(ToDoListMapper.toPO(toDoList));
    }

    @Override
    public Optional<ToDoList> findById(ToDoListId toDoListId) {
        return this.toDoListRepositoryPeer.findById(toDoListId.value()).map(ToDoListMapper::toDomain);
    }
}
