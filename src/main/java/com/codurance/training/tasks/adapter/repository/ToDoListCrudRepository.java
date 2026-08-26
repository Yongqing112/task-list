package com.codurance.training.tasks.adapter.repository;

import java.util.Optional;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.ToDoListMapper;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListCrudRepository implements ToDoListRepository {

    private ToDoListCrudRepositoryPeer peer;

    public ToDoListCrudRepository(ToDoListCrudRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(ToDoList toDoList) {
        this.peer.save(ToDoListMapper.toPO(toDoList));
    }

    @Override
    public void delete(ToDoList toDoList) {
        this.peer.delete(ToDoListMapper.toPO(toDoList));
    }

    @Override
    public Optional<ToDoList> findById(ToDoListId toDoListId) {
        return this.peer.findById(toDoListId.value()).map(ToDoListMapper::toDomain);
    }

}
